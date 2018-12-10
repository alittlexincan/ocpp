package com.zxyt.ocpp.publish.service.email;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: lxv
 * @Date: 2018/11/15 11:57
 * @Description:
 */
public interface IEmailService {
    /**
     * 邮件发布
     * @param json
     */
    void email(JSONObject json);
}
