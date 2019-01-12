package com.zxyt.ocpp.publish.service.impl.sina;


import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.entity.ChannelConfig;
import com.zxyt.ocpp.publish.mapper.channel.IChannelConfigMapper;
import com.zxyt.ocpp.publish.service.sina.ISinaWeiBoService;
import com.zxyt.ocpp.publish.util.XinLangWeiBoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 */
@Slf4j
@Service("sinaWeiBoService")
public class SinaWeiBoServiceImpl implements ISinaWeiBoService {

    /**
     * 注入渠道配置信息查询
     */
    @Autowired
    private IChannelConfigMapper channelConfigMapper;


    @Override
    @Async
    public void sinaWeiBo(JSONObject json) {

        JSONObject jsonObject = new JSONObject();

        //获取传真配置信息
        ChannelConfig channelConfig = channelConfigMapper.getSinaConfig();
        // 全局赋值
        JSONObject cc = JSONObject.parseObject(channelConfig.getContent());

        jsonObject.put("sinaSendUrl", cc.getString("sinaSendUrl"));
        jsonObject.put("safeUrl", cc.getString("safeUrl"));
        jsonObject.put("access_token", cc.getString("access_token"));
        jsonObject.put("content", json.getString("content"));

        try {
            JSONObject result = XinLangWeiBoUtil.sendPost(jsonObject);
            String error = result.getString("error");
            if(error!=null)log.info("failure...");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }







}
