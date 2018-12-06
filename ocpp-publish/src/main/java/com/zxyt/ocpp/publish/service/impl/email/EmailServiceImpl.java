package com.zxyt.ocpp.publish.service.impl.email;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.service.email.IEmailService;
import com.zxyt.ocpp.publish.util.SendMailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Properties;


/**
 * @Auther: lxv
 * @Date: 2018/11/15 13:00
 * @Description:
 */
@Slf4j
@Service("emailService")
public class EmailServiceImpl implements IEmailService {

    @Value("${mail.host}")
    private  String host;
    @Value("${mail.username}")
    private  String userName;
    @Value("${mail.password}")
    private  String passWord;
    @Value("${mail.port}")
    private  int port;
    @Value("${mail.defaultEncoding}")
    private  String defaultEncoding;

    /**
     * 发送邮件
     * @param json
     */
    @Override
    public void sendEmail(JSONObject json) {
        String url = json.getString("filePath");
        String title = json.getString("title");
        JSONArray users= json.getJSONArray("user");
        StringBuffer sb = new StringBuffer();
        users.forEach(item -> {
            JSONObject user = JSONObject.parseObject(item.toString());
            sb.append("," + user.getString("email"));
        });
        String[] userArray=new String[]{};
        if(sb.length() > 0){
            userArray = sb.substring(1).split(",");
        }
        Properties properties = new Properties();
        properties.setProperty("mail.debug", "false");// 是否显示调试信息(可选)
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.auth", "true");
        properties.put(" mail.smtp.timeout ", " 25000 ");
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setHost(host);
        javaMailSender.setUsername(userName); // 根据自己的情况,设置username
        javaMailSender.setPassword(passWord); // 根据自己的情况, 设置password
        javaMailSender.setPort(port);
        javaMailSender.setDefaultEncoding(defaultEncoding);
        try {
            SendMailUtils.sendWithAttament(javaMailSender,userArray,title,"",title+".doc",url);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
