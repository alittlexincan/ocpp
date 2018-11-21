package com.zxyt.ocpp.publish.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.page.MybatisPage;
import com.zxyt.ocpp.publish.config.common.universal.AbstractService;
import com.zxyt.ocpp.publish.entity.sys.UserGroup;
import com.zxyt.ocpp.publish.mapper.sys.IUserGroupMapper;
import com.zxyt.ocpp.publish.service.sys.IUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Service("userGroupService")
public class UserGroupServiceImpl extends AbstractService<UserGroup> implements IUserGroupService {

    @Autowired
    private IUserGroupMapper userGroupMapper;

    @Override
    public PageInfo<UserGroup> selectAll(Map<String, Object> map) {
        MybatisPage.getPageSize(map);
        PageHelper.startPage(MybatisPage.page, MybatisPage.limit);
        List<UserGroup> areaList = this.userGroupMapper.findAll(map);
        return new PageInfo<>(areaList);
    }

    @Override
    public UserGroup selectById(String id){
        return this.userGroupMapper.selectById(id);
    }

}
