package com.zxyt.ocpp.publish.service.impl.led;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.entity.ChannelConfig;
import com.zxyt.ocpp.publish.entity.Config;
import com.zxyt.ocpp.publish.mapper.channel.IChannelConfigMapper;
import com.zxyt.ocpp.publish.service.email.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @Auther: lxv
 * @Date: 2018/11/15 13:00
 * @Description:
 */
@Slf4j
@Service("emailService")
public class LedServiceImpl implements IEmailService {

    /**
     * 注入渠道配置信息查询
     */
    @Autowired
    private IChannelConfigMapper channelConfigMapper;

    /**
     * 邮件参数配置
     * @return
     */
    public Properties properties(){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.smtp.starttls.required", "true");
        properties.setProperty("mail.debug", "false");// 是否显示调试信息(可选)
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put(" mail.smtp.timeout ", " 25000 ");
        return properties;
    }

    /**
     * 配置邮件
     * @param
     * @return
     */
    private Config setEmail(){
        try {
            Config config = new Config();
            Map<String, Object> map = new HashMap<>();
            map.put("channelCode", "EMAIL");
            // 读取短信配置信息
            ChannelConfig channelConfig = this.channelConfigMapper.selectChannelConfig(map);
            // 解析邮件配置信息
            JSONObject mail = JSONObject.parseObject(channelConfig.getContent());
            // 邮件配置
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setJavaMailProperties(properties());
            mailSender.setDefaultEncoding("UTF-8");
            mailSender.setHost(mail.getString("host"));
            mailSender.setUsername(mail.getString("userName"));
            mailSender.setPassword(mail.getString("password"));
            mailSender.setPort(mail.getInteger("port"));
            config.setObject(mailSender);
            config.setJsonObject(mail);
            log.info("邮件配置成功");
            return config;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件配置查询配置失败，请检查配置项");
        }
        return null;
    }


    /**
     * 发送邮件
     * @param json
     */
    @Override
    @Async
    public void email(JSONObject json) {

        // 获取信息
        JSONObject info = getMessageUserInfo(json);

        log.info("邮件发送信息" + info);

        String[] users = info.getString("users").split(",");
        String title = json.getString("title");
        String content = json.getString("content");

        // 邮件配置
        Config config = setEmail();
        // 获取邮件配置信息
        JavaMailSender mailSender = (JavaMailSender) config.getObject();
        JSONObject obj = config.getJsonObject();
        // 消息组装
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(obj.getString("userName"));
        mailMessage.setTo(users);
        mailMessage.setSubject(title);
        mailMessage.setText(content);

        mailSender.send(mailMessage);
    }


    /**
     * 获取信息和用户
     * @param json
     * @return
     */
    private JSONObject getMessageUserInfo(JSONObject json){
        JSONObject result = new JSONObject();

        JSONArray channels = json.getJSONArray("channels");
        JSONObject groups = json.getJSONObject("groups");
        JSONObject users = json.getJSONObject("users");

        // 提取邮件受众
        JSONArray list = new JSONArray();
        channels.forEach(channelItem -> {
            JSONObject channel = JSONObject.parseObject(channelItem.toString());
            // 根据渠道获取对应的群组
            if(channel.getString("channelCode").equals("EMAIL")){
                JSONArray groupArray = groups.getJSONArray(channel.getString("channelId"));
                // 根据群组获取受众
                groupArray.forEach(group -> {
                    JSONObject g = JSONObject.parseObject(group.toString());
                    JSONArray userArray = users.getJSONArray(g.getString("userGroupId"));
                    userArray.forEach(user -> list.add(user));
                });
            }
        });

        // 存储即将发布的email地址
        StringBuffer sb = new StringBuffer();
        list.forEach(item -> {
            JSONObject user = JSONObject.parseObject(item.toString());
            sb.append("," + user.getString("userCode"));
        });
        result.put("title", json.getString("title"));
        result.put("content", json.getString("content"));
        result.put("users", sb.substring(1));
        return result;
    }
}
