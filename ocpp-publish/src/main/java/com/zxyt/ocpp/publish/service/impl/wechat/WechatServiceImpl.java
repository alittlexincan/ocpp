package com.zxyt.ocpp.publish.service.impl.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincan.utils.disaster.DisasterUtil;
import com.xincan.utils.disaster.MsgTypeUtil;
import com.zxyt.ocpp.publish.entity.WechatTemplate;
import com.zxyt.ocpp.publish.entity.WechatTemplateParam;
import com.zxyt.ocpp.publish.mapper.publish.ICallBackMapper;
import com.zxyt.ocpp.publish.service.wechat.IWechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: JiangXincan
 * @Description: 发布渠道：微信接口实现层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Slf4j
@Service("wechatService")
public class WechatServiceImpl implements IWechatService {

    @Value("${wechat.url}")
    private String url;

    @Value("${wechat.port}")
    private String port;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    // 气象灾害预警提醒
    @Value("${wechat.weather.template}")
    private String weatherTemplate;

    // 服务提醒通知
    @Value("${wechat.service.template}")
    private String serviceTemplate;

    // 突发事件预警提醒
    @Value("${wechat.warn.template}")
    private String warnTemplate;

    // 获取access_token url地址
    @Value("${wechat.token.url}")
    private String tokenUrl;

    // 模板请求路径
    @Value("${wechat.template.url}")
    private String templateUrl;

    // 用户OPENID列表信息地址

    @Value("${wechat.user.list.url}")
    private String userListUrl;

    // 单个用户信息地址
    @Value("${wechat.user.info.url}")
    private String userInfoUrl;

    // 发送指定用户（输入openId用英文逗号隔开）
    @Value("${wechat.ok.user}")
    private String okUser;

    // 每批次发送条数(微信平台要求上限不超过1000条每批次)
    @Value("${wechat.number}")
    private Integer number;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ICallBackMapper callBackMapper;

    @Override
    @Async
    public void wechat(JSONObject json) {

        JSONObject result = new JSONObject();
        log.info("===============微信推送=====================");
        // 1：通过tokenUrl获取access_token
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
        if(json.containsKey("template")){
            result = this.sendMessageTemplate(json, accessToken);
        }else {
            result = this.sendWarnTemplate(json, accessToken);
        }


        // 插入回执状态主表信息
        this.callBackMapper.insertMainMsg(result);


    }

    /**
     * 三天预报推送
     * @param json
     * @return
     */
    @Override
    public JSONObject pushThreeWeatherInfo(JSONObject json){

        JSONObject result = new JSONObject();

        // 1：通过tokenUrl获取access_token
        log.info("获取微信tokenUrl: 【{}】",tokenUrl);
        JSONObject token = this.restTemplate.getForObject(tokenUrl,JSONObject.class);
        String accessToken = token.getString("access_token");
        if(accessToken == null){
            log.info("微信推送获取TOKEN失败");
            result.put("code", 500);
            result.put("msg", "微信推送获取TOKEN失败");
            return result;
        }
        log.info("成功获取微信token: 【{}】" ,accessToken);

        return this.sendThreeWeather(json, accessToken);

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
        List<String> userList = okUser.length() > 0 ? Arrays.asList(okUser.split(",")) : getUserList(accessToken, "");
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
     * 发送预警模板消息
     * @param json
     * @param accessToken
     * @return
     */
    private JSONObject sendWarnTemplate(JSONObject json, String accessToken){
        // 存储发送结果主表信息
        JSONObject main = new JSONObject();
        // 2：获取所有关注用户信息，提取用户openId: 如果okUser有值，则说明是手动发送
        List<String> userList = okUser.length() > 0 ? Arrays.asList(okUser.split(",")) : getUserList(accessToken, "");
        log.info("微信用户openId列表：{}",JSON.toJSON(userList));
        // 3：获取灾种信息
        JSONObject disaster = DisasterUtil.getDisasterInfo(json.getInteger("disasterColor"));
        // 获取灾种名称
        String disasterName = json.getString("disasterName")
        // 获取灾种颜色(rgb)
        , rgb = disaster.getString("rgb")
        // 获取灾种颜色(名称)
        ,color = disaster.getString("color");
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
                tem.setTemplateId(warnTemplate);
                tem.setTopColor(rgb);
                tem.setToUser(u);
                tem.setUrl("");
                List<WechatTemplateParam> paras = new ArrayList<>();
                paras.add(new WechatTemplateParam("first",getWarnTitle(json, disaster), rgb));
                paras.add(new WechatTemplateParam("keyword1",json.getString("organizationName"), rgb));
                paras.add(new WechatTemplateParam("keyword2",disasterName, rgb));
                paras.add(new WechatTemplateParam("keyword3",color, rgb));
                paras.add(new WechatTemplateParam("keyword4",json.getString("sendTime"), rgb));
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
     * 三天预报模板推送：模板类型：服务模板
     */
    private JSONObject sendThreeWeather(JSONObject json, String accessToken){

        JSONObject result = new JSONObject();

        // 2：获取所有关注用户信息，提取用户openId: 如果okUser有值，则说明是手动发送
        List<String> userList = okUser.length() > 0 ? Arrays.asList(okUser.split(",")) : getUserList(accessToken, "");

        // 获取用户总个数
        int count = userList.size();
        if(count <= 0){
            log.info("微信暂无关注用户");
            result.put("code",404);
            result.put("code","微信暂无关注用户");
            return result;
        }
        for (int i = 0; i < count; i += this.number) {
            if (i + this.number > count) {        //作用为number最后没有200条数据则剩余几条newList中就装几条
                this.number = count - i;
            }
            StringBuilder sb = new StringBuilder();
            String rgb = "#3F48CC";
            userList.subList(i, i + this.number).forEach(u -> {
                sb.append("," + u);
                WechatTemplate tem = new WechatTemplate();
                tem.setTemplateId(this.serviceTemplate);
                tem.setTopColor(rgb);
                tem.setToUser(u);
                tem.setUrl("");
                List<WechatTemplateParam> paras = new ArrayList<>();
                paras.add(new WechatTemplateParam("first",json.getString("title"), rgb));
                paras.add(new WechatTemplateParam("keyword1",json.getString("type"), rgb));
                paras.add(new WechatTemplateParam("keyword2",json.getString("time"), rgb));
                paras.add(new WechatTemplateParam("remark",json.getString("content"), rgb));
                tem.setTemplateParamList(paras);
                log.info("模板信息：" + tem.toJSON());
                ResponseEntity<JSONObject> rest = this.restTemplate.postForEntity(templateUrl.replace("{accessToken}", accessToken), tem.toJSON(), JSONObject.class);
                log.info("微信推送回执结果：" + rest.getBody().toJSONString());
            });
            log.info("微信每批次发送用户：" + sb.toString().substring(1));
        }
        result.put("code",200);
        result.put("msg","三天预报推送成功");
        return result;
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
     * 获取预警模板标题
     *
     * @param json
     * @return
     */
    private String getWarnTitle(JSONObject json, JSONObject disaster){
        String title = json.getString("organizationName");
        String msgType = json.getString("msgType");
        title += MsgTypeUtil.parseMsgType(msgType);
        title += json.getString("disasterName");
        title += disaster.getString("color");
        title += "预警信号";
        title += disaster.getString("level");
        return title;
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
