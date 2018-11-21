package com.zxyt.ocpp.publish.service.sys;

import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.universal.IBaseService;
import com.zxyt.ocpp.publish.entity.sys.Menu;

import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 菜单管理接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IMenuService extends IBaseService<Menu> {

    PageInfo<Menu> selectAll(Map<String, Object> map);

}
