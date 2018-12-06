package com.zxyt.ocpp.client.service.sys;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.client.config.common.universal.IBaseService;
import com.zxyt.ocpp.client.entity.sys.Permission;
import com.zxyt.ocpp.client.entity.sys.Role;

import java.util.List;
import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 员工接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 分页查询角色信息
     * @param map
     * @return
     */
    PageInfo<Role> selectAll(Map<String, Object> map);

    /**
     * 根据ID查询对应的角色
     * @param map
     * @return
     */
    List<Role> selectById(Map<String, Object> map);

    /**
     * 查询角色信息
     * @param map
     * @return
     */
    List<Role> selectByRoleName(Map<String, Object> map);

    /**
     * 角色配置菜单
     * @param map
     * @return
     */
    int insertRoleMenu(Map<String, Object> map);

    /**
     * 根据角色ID查询对应的菜单信息
     * @param map
     * @return
     */
    JSONArray selectRoleInMenu(Map<String, Object> map);

    /**
     * 角色配置权限
     * @param map
     * @return
     */
    int insertRolePermission(Map<String, Object> map);

    /**
     * 根据角色ID查询对应的权限信息
     * @param map
     * @return
     */
    List<Permission> selectRoleInPermission(Map<String, Object> map);

    /**
     * 根据角色ID删除角色
     * @param id
     * @return
     */
    int deleteByRoleId(String id);

    /**
     * 根据一批角色ID删除角色
     * @param id
     * @return
     */
    int deleteByRoleIds(String id);


}
