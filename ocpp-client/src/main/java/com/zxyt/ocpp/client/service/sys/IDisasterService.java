package com.zxyt.ocpp.client.service.sys;

import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.client.config.common.universal.IBaseService;
import com.zxyt.ocpp.client.entity.sys.Disaster;

import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 灾种管理接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IDisasterService extends IBaseService<Disaster> {

    PageInfo<Disaster> selectAll(Map<String, Object> map);

    Disaster selectById(String id);

    /**
     * 更新策略配置，是否进行过策略配置
     * @param map
     * @return
     */
    int updateStrategyById(Map<String, Object> map);

}
