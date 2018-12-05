package com.zxyt.ocpp.publish.service.message;

import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.config.common.universal.IBaseService;
import com.zxyt.ocpp.publish.entity.message.Message;

import java.util.Map;

/**
 * 一键发布信息接口
 */
public interface IMessageService  extends IBaseService<Message> {

    Message insert(Map<String, Object> map);

    /**
     * 获取文件信息
     * @param map
     * @return
     */
    JSONObject selectFTPFileInfo(Map<String, Object> map);

}
