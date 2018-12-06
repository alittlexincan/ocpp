package com.zxyt.ocpp.publish.service.sms;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: JiangXincan
 * @Description: 发布渠道：短信接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface ISmsService {

    void sms(JSONObject json);
}
