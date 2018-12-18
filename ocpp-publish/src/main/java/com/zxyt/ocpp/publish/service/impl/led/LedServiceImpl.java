package com.zxyt.ocpp.publish.service.impl.led;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.entity.ChannelConfig;
import com.zxyt.ocpp.publish.entity.Config;
import com.zxyt.ocpp.publish.mapper.channel.IChannelConfigMapper;
import com.zxyt.ocpp.publish.service.email.IEmailService;
import com.zxyt.ocpp.publish.service.led.ILedService;
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
@Service("ledService")
public class LedServiceImpl implements ILedService {

    /**
     * 注入渠道配置信息查询
     */
    @Autowired
    private IChannelConfigMapper channelConfigMapper;



    @Override
    public void led(JSONObject json) {

    }
}
