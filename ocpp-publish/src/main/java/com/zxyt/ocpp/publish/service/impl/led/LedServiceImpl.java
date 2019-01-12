package com.zxyt.ocpp.publish.service.impl.led;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.entity.ChannelConfig;
import com.zxyt.ocpp.publish.entity.Ftp;
import com.zxyt.ocpp.publish.mapper.channel.IChannelConfigMapper;
import com.zxyt.ocpp.publish.service.led.ILedService;
import com.zxyt.ocpp.publish.util.FtpOfFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


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

    @Value("${web.upload-path}")
    private String uploadPath;

    @Override
    @Async
    public void led(JSONObject json) {

        //获取传真配置信息
        ChannelConfig channelConfig = channelConfigMapper.getLedFTPConfig();
        Ftp ftp = new Ftp();
        // 全局赋值
        JSONObject cc = JSONObject.parseObject(channelConfig.getContent());
        ftp.setHost(cc.getString("host"));
        ftp.setPort(Integer.parseInt(cc.getString("port")));
        ftp.setUser(cc.getString("user"));
        ftp.setPassword(cc.getString("password"));
        String url = null;
        try {
            JSONArray files = json.getJSONArray("files");
            if(files != null){
                for(int i = 0; i < files.size(); i++){
                    JSONObject file = files.getJSONObject(i);
                    url = file.getString("url");
                }
                //上传文件到ftp
                boolean flag = FtpOfFileUtil.uploadFile(uploadPath+url, "天气预报.txt", ftp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
