package com.zxyt.ocpp.publish.service.impl.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincan.utils.disaster.DisasterUtil;
import com.xincan.utils.disaster.MsgTypeUtil;
import com.zxyt.ocpp.publish.entity.ChannelConfig;
import com.zxyt.ocpp.publish.entity.WechatTemplate;
import com.zxyt.ocpp.publish.entity.WechatTemplateParam;
import com.zxyt.ocpp.publish.mapper.channel.IChannelConfigMapper;
import com.zxyt.ocpp.publish.mapper.publish.ICallBackMapper;
import com.zxyt.ocpp.publish.service.wechat.IWechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Author: JiangXincan
 * @Description: 发布渠道：微信接口实现层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Slf4j
@Service("wechatService")
public class WechatServiceImpl implements IWechatService {


    // 微信应用APPID
    private String appId;

    // 微信应用APPSECRET
    private String appSecret;

    // 服务提醒通知
    private String serviceTemplate;

    // 获取access_token url地址
    private String tokenUrl;

    // 模板请求路径
    private String templateUrl;

    // 用户OPENID列表信息地址
    private String userListUrl;

    // 发送指定用户（输入openId用英文逗号隔开）
    private String okUser;

    // 每批次发送条数(微信平台要求上限不超过1000条每批次)
    private Integer number;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 注入渠道配置信息
     */
    @Autowired
    private IChannelConfigMapper channelConfigMapper;

    @Autowired
    private ICallBackMapper callBackMapper;

    @Override
    @Async
    public void wechat(JSONObject json) {

        System.out.println(json);

        JSONObject result = new JSONObject();

        // 1：初始化加载微信配置信息
        readSMSConfigInfo(json);

        log.info("===============微信推送=====================");
        // 2：通过tokenUrl获取access_token
        log.info("获取微信tokenUrl: 【{}】",tokenUrl);
        JSONObject token = this.restTemplate.getForObject(tokenUrl,JSONObject.class);
        String accessToken = token.getString("access_token");
        if(accessToken == null){
            log.info("微信推送获取TOKEN失败！{}", token);
            result.put("messageId", json.getString("id"));
            result.put("channelCode", "WECHAT");
            result.put("total", 1);
            result.put("success", 0);
            result.put("fail", 1);
            result.put("work","微信推送获取TOKEN失败");
        }
        log.info("成功获取微信token: 【{}】" ,accessToken);

        result = this.sendMessageTemplate(json, accessToken);

        // 插入回执状态主表信息
        this.callBackMapper.insertMainMsg(result);

    }


    /**
     * 发送一键式发布模板消息
     * @param json
     * @param accessToken
     * @return
     */
    private JSONObject sendMessageTemplate(JSONObject json, String accessToken){
        // 存储发送结果主表信息
        JSONObject main = new JSONObject();
        // 2：获取所有关注用户信息，提取用户openId: 如果okUser有值，则说明是手动发送
        List<String> userList = this.okUser.length() > 0 ? Arrays.asList(this.okUser.split(",")) : getUserList(accessToken, "");
        log.info("微信用户openId列表：{}",JSON.toJSON(userList));
        // 设置模板字体颜色
        String rgb = "#000000";
        // 获取用户总个数
        int count = userList.size();
        if(count <= 0){
            log.info("微信暂无关注用户");
            return this.callBackMsg(json.getString("id"), "微信暂无关注用户");
        }
        for (int i = 0; i < count; i += this.number) {
            if (i + this.number > count) {        //作用为number最后没有200条数据则剩余几条newList中就装几条
                this.number = count - i;
            }
            StringBuilder sb = new StringBuilder();
            userList.subList(i, i + this.number).forEach(u -> {
                sb.append("," + u);
                WechatTemplate tem = new WechatTemplate();
                tem.setTemplateId(serviceTemplate);
                tem.setTopColor(rgb);
                tem.setToUser(u);
                tem.setUrl("");
                List<WechatTemplateParam> paras = new ArrayList<>();
                paras.add(new WechatTemplateParam("first",json.getString("title"), rgb));
                paras.add(new WechatTemplateParam("keyword1",MsgTypeUtil.parseOneType(json.getInteger("type")), rgb));
                paras.add(new WechatTemplateParam("keyword2",json.getString("sendTime"), rgb));
                paras.add(new WechatTemplateParam("remark",json.getJSONObject("content").getString("content"), rgb));
                tem.setTemplateParamList(paras);
                log.info("模板信息：" + tem.toJSON());
                ResponseEntity<JSONObject> rest = this.restTemplate.postForEntity(templateUrl.replace("{accessToken}", accessToken), tem.toJSON(), JSONObject.class);
                log.info("微信推送回执结果：" + rest.getBody().toJSONString());
            });
            log.info("微信每批次发送用户：" + sb.toString().substring(1));
        }
        log.info("微信推送成功");
        main.put("messageId", json.getString("id"));
        main.put("channelCode", "WECHAT");
        main.put("total", 1);
        main.put("success", 1);
        main.put("fail", 0);
        main.put("work","微信推送成功");
        return main;
    }



    /**
     * 递归获取所有用户openId
     * @param accessToken
     * @param userOpenId  下一个openId
     */
    private List<String> getUserList(String accessToken, String userOpenId){
        List<String> userList = new ArrayList<>();
        Integer count = -1, total = 0;
        String next_openId = userOpenId;
        while (count==-1 || count > 0){
            if(total.equals(count)) break;
            String userUrl = this.userListUrl.replace("{accessToken}", accessToken).replace("{userOpenId}",next_openId);
            JSONObject users = this.restTemplate.getForObject(userUrl, JSONObject.class);
            next_openId = users.getString("next_openid");
            count = users.getInteger("count");
            total = users.getInteger("total");
            if(next_openId == null){
                count = 0;
                log.info("微信获取用户列表失败  {}", users);
            }else {
                if(count == 0)return userList;
                JSONArray userArray = users.getJSONObject("data").getJSONArray("openid");
                List<String> list = JSONObject.parseArray(userArray.toJSONString(), String.class);
                userList.addAll(list);
            }
        }
        return userList;
    }

    /**
     * 读取短信配置信息并设置全局参数信息
     * @param json
     */
    private int readSMSConfigInfo(JSONObject json){
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("areaId", json.getString("areaId"));
            map.put("organizationId", json.getString("organizationId"));
            map.put("channelCode", "WECHAT");
            // 读取短信配置信息
            ChannelConfig config = this.channelConfigMapper.selectChannelConfig(map);

            System.out.println(config);

            // 全局赋值
            JSONObject cc = JSONObject.parseObject(config.getContent());

            this.appId = cc.getString("appId");                                                         // 微信应用ID
            this.appSecret = cc.getString("appSecret");                                                 // 微信应用密钥
            this.tokenUrl = cc.getString("tokenUrl") + "?grant_type=client_credential&appid=" + this.appId + "&secret=" + this.appSecret;      // 微信应用密钥
            this.serviceTemplate = cc.getString("serviceTemplate");             // 服务提醒模板
            this.templateUrl = cc.getString("templateUrl") + "?access_token={accessToken}";                     // 模板推送服务提醒消息路径
            this.userListUrl = cc.getString("userListUrl") + "?access_token={accessToken}&next_openid={userOpenId}"; // 用户列表地址
            this.okUser = cc.getString("okUser");                               // 指定发送用户
            this.number = cc.getInteger("number");                              // 获取发送条数

            log.info("获取微信配置信息成功");
            return 200;
        }catch (Exception e){
            log.error("获取微信配置信息失败");
        }
        return 500;
    }

    /**
     * 状态报告错误回执信息处理
     * @param id
     * @param msg
     * @return
     */
    private JSONObject callBackMsg(String id, String msg){
        JSONObject json = new JSONObject();
        json.put("messageId", id);
        json.put("channelCode", "WECHAT");
        json.put("total", 1);
        json.put("success", 0);
        json.put("fail", 1);
        json.put("work",msg);
        return json;
    }
}
