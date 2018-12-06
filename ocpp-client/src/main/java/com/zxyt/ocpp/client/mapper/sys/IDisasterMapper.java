package com.zxyt.ocpp.client.mapper.sys;


import com.zxyt.ocpp.client.config.common.universal.IBaseMapper;
import com.zxyt.ocpp.client.entity.sys.Disaster;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("disasterMapper")
public interface IDisasterMapper extends IBaseMapper<Disaster> {

    /**
     * 分页查询渠道手段信息
     * @param map
     * @return
     */
    List<Disaster> findAll(Map<String, Object> map);

    /**
     * 根据地区id查询渠道手段信息
     * @param id
     * @return
     */
    Disaster selectById(@Param(value = "id") String id);

    /**
     * 更新策略配置，是否进行过策略配置
     * @param map
     * @return
     */
    int updateStrategyById(Map<String, Object> map);

}
