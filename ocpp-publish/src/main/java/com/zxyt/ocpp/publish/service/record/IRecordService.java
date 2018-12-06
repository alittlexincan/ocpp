package com.zxyt.ocpp.publish.service.record;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: JiangXincan
 * @Description: 国突备案接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IRecordService {

    /**
     * 国突对接（ftp方式）
     * @param json
     * @return
     */
    void record(JSONObject json);
}
