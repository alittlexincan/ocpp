package com.zxyt.ocpp.publish.service.impl.sms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincan.utils.md5.MD5Util;
import com.zxyt.ocpp.publish.mapper.publish.ICallBackMapper;
import com.zxyt.ocpp.publish.service.sms.ISmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * @Author: JiangXincan
 * @Description: 发布渠道：短信接口实现类
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Slf4j
@Service("smsService")
public class SmsServiceImpl implements ISmsService {


    @Value("${sms.ec.name}")
    private String ecName;

    @Value("${sms.user.name}")
    private String userName;

    @Value("${sms.user.password}")
    private String userPassword;

    /**
     * 短信发送签名
     */
    @Value("${sms.sign}")
    private String sign;

    /**
     * 短信授权用户名称
     */
    @Value("${sms.http.authorize.user.name}")
    private String authorizeUserName;

    /**
     * 短信授权用户密码
     */
    @Value("${sms.http.authorize.user.password}")
    private String authorizeUserPassword;

    /**
     * 短信授权接口调用URL
     */
    @Value("${sms.authorize.url}")
    private String authorizeUrl;

    /**
     * 短信发送接口调用URL
     */
    @Value("${sms.send.url}")
    private String sendUrl;

    /**
     * 每批次发送条数(云MAS平台要求上限不超过200条每批次)
     */
    @Value("${sms.number}")
    private Integer number;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ICallBackMapper callBackMapper;

    /**
     * 短信发送
     * 1：解析数据
     * 2：短信发送授权
     * 3：短信发送
     * @param json
     */
    @Override
    @Async
    public void sms(JSONObject json) {

        // 存储发送结果主表信息
        JSONObject main = new JSONObject();
        // 1：解析数据
        JSONObject sendMessage = setMessage(json);
        // 获取发送用户
        JSONArray userArray = sendMessage.getJSONArray("userArray");
        // 获取发送内容
        String content = sendMessage.getString("content");
        // 2：短信发送授权获取mas_user_id用户登录id
        String authorizeUrl = this.authorizeUrl + "?ec_name=" + this.ecName + "&user_name=" + this.authorizeUserName + "&user_passwd=" + this.authorizeUserPassword;
        log.info("短信授权URL：【" + authorizeUrl + "】");
        JSONObject authorize = this.restTemplate.getForObject(authorizeUrl,JSONObject.class);
        log.info("短信授权返回信息：【" + authorizeUrl +"】");
        // 获取用户登录ID
        String mas_user_id = authorize.getString("mas_user_id");
        // 获取access_token
        String access_token = authorize.getString("access_token");
        if(mas_user_id == null){
            log.info("短信发送授权失败");
            main.put("messageId", json.getString("id"));
            main.put("channelCode", "SMS");
            main.put("total", userArray.size());
            main.put("success", 0);
            main.put("fail", 0);
            main.put("work","云MAS短信发送授权失败");
        }else {

            // 存储发送结果字表信息
            JSONArray childArray = new JSONArray();
            // 发送速率开始时间（限制10条/秒）
            long start = System.currentTimeMillis();
            int num = 0;
            for (int i = 0; i < userArray.size(); i++) {
                // 受众编码
                String phone = userArray.getString(i);
                // 拼接发送参数
                JSONObject param = new JSONObject();
                param.put("mas_user_id", mas_user_id);
                param.put("access_token", access_token);
                param.put("content", content);
                param.put("userList", phone);
                // 短信发送
                JSONObject send = send(param);
                // 短信回执信息
                String retCode = send.getString("RET-CODE");
                if(retCode.equals("SC:0000")){
                    num++;
                    // 发送成功记录
                    JSONObject success = new JSONObject();
                    success.put("messageId",json.getString("id"));
                    success.put("channelCode", "SMS");
                    success.put("code", phone);
                    success.put("status", 0);
                    childArray.add(success);
                }else{
                    // 发送失败记录
                    JSONObject fail = new JSONObject();
                    fail.put("messageId",json.getString("id"));
                    fail.put("channelCode", "SMS");
                    fail.put("code", phone);
                    fail.put("status", 1);
                    childArray.add(fail);
                }
                // 短信限流处理
                if((i+1)/10==0){
                    // 发送速率结束时间
                    long end = System.currentTimeMillis();
                    // 计算时间差，如果小于1秒则等待时间差正好凑够1秒，为了防止有误在加50毫秒间隙，官方要求10条/秒，要留够时间间隙
                    long res = end - start;
                    if(res < 1000){
                        try{
                            Thread.sleep(1000- res + 50);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    start = start + 1000 + 50;
                }
            }
            main.put("messageId", json.getString("id"));
            main.put("channelCode", "SMS");
            main.put("total", userArray.size());
            main.put("success", num);
            main.put("fail", userArray.size() - num);
            main.put("work","云MAS短信推送成功");

            // 插入回执状态主表信息
            this.callBackMapper.insertMainMsg(main);
            // 插入回执状态字表信息
            this.callBackMapper.insertChildMsg(childArray);

        }
    }


    /**
     * 短信发送
     * @param json      基础传输数据
     * @return
     */
    private JSONObject send(JSONObject json){
        String mas_user_id = json.getString("mas_user_id")
                ,userList = json.getString("userList")
                ,content = json.getString("content")
                ,access_token = json.getString("access_token");
        // 3：短信加密属性
        String mac = MD5Util.md5toUpperCase32(mas_user_id + userList + content + this.sign + "" + access_token);
        // 4：短信发送路径
        String sendUrl = this.sendUrl + "?mas_user_id=" + mas_user_id + "&mobiles=" + userList + "&content=" + content + "&sign=" + this.sign + "&serial=&mac=" + mac;
        log.info("短信发送URL：【" + sendUrl + "】");
        // 5：短信发送
        return this.restTemplate.postForObject(sendUrl, "", JSONObject.class);

    }

    /**
     * 获取短信发送信息和受众
     * @param param
     * @return
     */
    private JSONObject setMessage(JSONObject param){
        JSONObject result = new JSONObject();
        // 获取发送内容
        String content = param.getJSONObject("content").getString("content");
        // 获取发送受众
        JSONObject user = param.getJSONObject("user");
        JSONArray array = new JSONArray();
        for (String u : user.keySet()) {
            user.getJSONArray(u).forEach( us ->{
                JSONObject j = (JSONObject) us;
                if(j.getString("channelCode").equals("SMS")){
                    array.add(j.getString("userCode"));
                }
            });
        }
        result.put("content", content);
        result.put("userArray", array);
        return result;
    }

}
