package com.zxyt.ocpp.client.service.sys;

import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.client.config.common.universal.IBaseService;
import com.zxyt.ocpp.client.entity.sys.UserGroup;

import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 群组管理接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IUserGroupService extends IBaseService<UserGroup> {

    PageInfo<UserGroup> selectAll(Map<String, Object> map);

    UserGroup selectById(String id);

}
