package com.zxyt.ocpp.publish.service.impl.fax;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincan.utils.ftp.FTPConfig;
import com.xincan.utils.ftp.FTPUtil;
import com.zxyt.ocpp.publish.entity.ChannelConfig;
import com.zxyt.ocpp.publish.entity.Config;
import com.zxyt.ocpp.publish.mapper.channel.IChannelConfigMapper;
import com.zxyt.ocpp.publish.service.email.IEmailService;
import com.zxyt.ocpp.publish.service.fax.IFaxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @Auther: lxv
 * @Date: 2018/11/15 13:00
 * @Description:
 */
@Slf4j
@Service("faxService")
public class FaxServiceImpl implements IFaxService {

    /**
     * 注入渠道配置信息查询
     */
    @Autowired
    private IChannelConfigMapper channelConfigMapper;




    /**
     * 发送邮件
     * @param json
     */
    @Override
    @Async
    public void fax(JSONObject json) {

        //获取传真配置信息
        ChannelConfig channelConfig = channelConfigMapper.getFTPConfig();
        FTPConfig ftpConfig = new FTPConfig();
        // 全局赋值
        JSONObject cc = JSONObject.parseObject(channelConfig.getContent());
        ftpConfig.setHost(cc.getString("host"));
        ftpConfig.setPort(Integer.parseInt(cc.getString("port")));
        ftpConfig.setUser(cc.getString("user"));
        ftpConfig.setPassword(cc.getString("password"));
//        if(FTPUtil.login(ftpConfig)) {
//            // 文件开始上传
//            for (MultipartFile file : files) {
//                // 获取文件名
//                String fileName = file.getOriginalFilename();
//                // 获取文件后缀
//                String prefix=fileName.substring(fileName.lastIndexOf("."));
//                //创建临时文件
//                File excelFile = File.createTempFile(fileName, prefix);
//                // MultipartFile to File
//                file.transferTo(excelFile);
//                boolean isfile = FTPUtil.uploadFile(excelFile, fileName);
//            }
//            FTPUtil.close();
//        }


    }



}
