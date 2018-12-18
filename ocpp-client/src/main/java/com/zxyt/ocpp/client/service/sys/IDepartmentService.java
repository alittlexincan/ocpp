package com.zxyt.ocpp.client.service.sys;

import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.client.config.common.universal.IBaseService;
import com.zxyt.ocpp.client.entity.sys.Department;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author: LiWeidong
 * @Description: 部门管理接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IDepartmentService extends IBaseService<Department> {

    PageInfo<Department> selectAll(Map<String, Object> map);

    Department selectById(@Param(value = "id") String id);

}
