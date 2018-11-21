package com.zxyt.ocpp.publish.service.sys;

import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.universal.IBaseService;
import com.zxyt.ocpp.publish.entity.sys.Permission;

import java.util.List;
import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 员工接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IPermissionService extends IBaseService<Permission> {

    PageInfo<Permission> selectAll(Map<String, Object> map);

    List<Permission> selectById(Map<String, Object> map);

    List<Permission> selectByPermissionName(Map<String, Object> map);

}
