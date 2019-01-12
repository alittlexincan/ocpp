package com.zxyt.ocpp.publish.service.impl.fax;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincan.utils.ftp.FTPConfig;
import com.xincan.utils.ftp.FTPUtil;
import com.zxyt.ocpp.publish.entity.ChannelConfig;
import com.zxyt.ocpp.publish.mapper.channel.IChannelConfigMapper;
import com.zxyt.ocpp.publish.service.fax.IFaxService;
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
@Service("faxService")
public class FaxServiceImpl implements IFaxService {



    /**
     * 注入渠道配置信息查询
     */
    @Autowired
    private IChannelConfigMapper channelConfigMapper;

    @Value("${web.upload-path}")
    private String uploadPath;


    /**
     * 发送文件到ftp，然后在传真机上显示。
     * @param json
     */
    @Override
    @Async
    public void fax(JSONObject json) {

        //获取传真配置信息
        ChannelConfig channelConfig = channelConfigMapper.getFaxFTPConfig();
        FTPConfig ftpConfig = new FTPConfig();
        // 全局赋值
        JSONObject cc = JSONObject.parseObject(channelConfig.getContent());
        ftpConfig.setHost(cc.getString("host"));
        ftpConfig.setPort(Integer.parseInt(cc.getString("port")));
        ftpConfig.setUser(cc.getString("user"));
        ftpConfig.setPassword(cc.getString("password"));
        String url = "null";
        try {
            if(FTPUtil.login(ftpConfig)) {
                JSONArray files = json.getJSONArray("files");
                if(files != null){
                    for(int i = 0; i<files.size(); i++){
                        JSONObject file = files.getJSONObject(i);
                        url = file.getString("url");
                    }
                    //上传文件到ftp
                    boolean flag = FTPUtil.uploadFile(uploadPath+url, "天气预报.txt");
                }
                //FTPUtil.close();//关闭资源
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
