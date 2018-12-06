package com.zxyt.ocpp.client.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.client.config.common.page.MybatisPage;
import com.zxyt.ocpp.client.config.common.universal.AbstractService;
import com.zxyt.ocpp.client.entity.sys.Permission;
import com.zxyt.ocpp.client.mapper.sys.IPermissionMapper;
import com.zxyt.ocpp.client.service.sys.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: XincanJiang
 * @Description:
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Service("permissionService")
public class PermissionServiceImpl extends AbstractService<Permission> implements IPermissionService {

    @Autowired
    private IPermissionMapper permissionMapper;

    @Override
    public PageInfo<Permission> selectAll(Map<String, Object> map) {
        MybatisPage.getPageSize(map);
        PageHelper.startPage(MybatisPage.page, MybatisPage.limit);
        List<Permission> areaList = this.permissionMapper.findAll(map);
        return new PageInfo<>(areaList);
    }

    @Override
    public List<Permission> selectById(Map<String, Object> map){
        return this.permissionMapper.findPermissionByRoleId(map);
    }

    @Override
    public List<Permission> selectByPermissionName(Map<String, Object> map){
        return this.permissionMapper.selectByPermissionName(map);
    }

}
