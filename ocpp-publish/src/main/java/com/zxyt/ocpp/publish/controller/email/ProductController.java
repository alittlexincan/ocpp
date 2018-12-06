package com.zxyt.ocpp.publish.controller.email;

import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.service.email.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lxv
 * @Date: 2018/11/15 11:17
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("email")
public class ProductController {

    @Autowired
    private IEmailService emailService;

    /**
     * 发送邮件信息
     * @param json
     */
    @PostMapping("send")
    public void send(@RequestBody JSONObject json){
        log.info("产品制作发送接口数据：" + json);
        if(json.getInteger("code") == 200){
            JSONObject data = json.getJSONObject("data");
            for(String key : data.keySet()){
                if(key.equals("email")) this.emailService.sendEmail(data.getJSONObject(key));
            }
        }
    }

}
