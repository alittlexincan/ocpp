package com.zxyt.ocpp.client.mapper.sys;

import com.zxyt.ocpp.client.config.common.universal.IBaseMapper;
import com.zxyt.ocpp.client.entity.sys.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: LiWeidong
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("departmentMapper")
public interface IDepartmentMapper extends IBaseMapper<Department> {

    /**
     * 分页查询部门信息
     * @param map
     * @return
     */
    List<Department> findAll(Map<String, Object> map);

    /**
     * 根据地区id查询部门信息
     * @param id
     * @return
     */
    Department selectById(@Param(value = "id") String id);

    /**
     * 根据部门code，部门类型查询部门信息
     * @param map
     * @return
     */
    Department selectDepartmentByCode(Map<String, Object> map);
    
}
