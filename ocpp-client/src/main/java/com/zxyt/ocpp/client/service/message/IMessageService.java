package com.zxyt.ocpp.client.service.message;

import com.alibaba.fastjson.JSONObject;
import com.xincan.utils.ftp.FTPConfig;
import com.zxyt.ocpp.client.config.common.universal.IBaseService;
import com.zxyt.ocpp.client.entity.message.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 一键发布信息接口
 */
public interface IMessageService  extends IBaseService<Message> {

    JSONObject insert(Map<String, Object> map,MultipartFile[] files);

    /**
     * 获取文件信息
     * @param map
     * @return
     */
    JSONObject selectFTPFileInfo(Map<String, Object> map);

    /**
     * 获取FTP信息
     * @return
     */
    FTPConfig  getFTPConfig();
}
