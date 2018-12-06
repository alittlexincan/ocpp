package com.zxyt.ocpp.publish.controller.publish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.service.record.IRecordService;
import com.zxyt.ocpp.publish.service.sms.ISmsService;
import com.zxyt.ocpp.publish.service.wechat.IWechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 信息发布接口控制层
 * @Date: Created in 18:04 2018-4-18
 * @Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/publish")
@EnableAsync
public class PublishController {

    /**
     * 发布渠道：对接微信接口
     */
    @Autowired
    private ISmsService smsService;

    /**
     * 发布渠道：对接微信接口
     */
    @Autowired
    private IWechatService wechatService;

    /**
     * 国突平台对接接口
     */
    @Autowired
    private IRecordService recordService;


    /**
     * 渠道发布
     * @param json
     */
    @PostMapping("/")
    public void publish(@RequestBody JSONObject json){
        log.info("接收推送数据：" + json.toJSONString());
        JSONArray channelArray = json.getJSONArray("channels");
        for(int i = 0; i<channelArray.size(); i++){
            // 获取渠道编码
            String code = channelArray.getJSONObject(i).getString("channelCode");
            // 短信
            if(code.equals("SMS")) this.smsService.sms(json);
            // 微信
            if(code.equals("WECHAT")) this.wechatService.wechat(json);
        }

        // 如果record国突标识为1，则需要对接国突
        // 生成CAP协议文件，将其通过FTP上传到国突平台对应的文件夹下
        if(json.containsKey("record")){
            int record = json.getInteger("record");
            if(record == 1){
                recordService.record(json);
            }
        }
    }

}
