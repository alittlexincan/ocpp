package com.zxyt.ocpp.publish.service.led;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: Liweidong
 * @Description: 发布渠道：显示屏接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface ILedService {

    void led(JSONObject json);
}
