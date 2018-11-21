package com.zxyt.ocpp.publish.mapper.sys;


import com.zxyt.ocpp.publish.entity.sys.Area;
import com.zxyt.ocpp.publish.config.common.universal.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;



/**
 * @Author: XincanJiang
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("areaMapper")
public interface IAreaMapper extends IBaseMapper<Area> {

    /**
     * 分页查询地区信息
     * @param map
     * @return
     */
    List<Area> findAll(Map<String, Object> map);

    /**
     * 根据地区id查询地区信息
     * @param id
     * @return
     */
    Area selectById(@Param(value="id") String id);

}
