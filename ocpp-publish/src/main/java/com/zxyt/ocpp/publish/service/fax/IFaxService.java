package com.zxyt.ocpp.publish.service.fax;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: Liweidong
 * @Description: 发布渠道：传真接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IFaxService {

    void fax(JSONObject json);
}
