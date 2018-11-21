package com.zxyt.ocpp.publish.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.page.MybatisPage;
import com.zxyt.ocpp.publish.config.common.universal.AbstractService;
import com.zxyt.ocpp.publish.entity.sys.Menu;
import com.zxyt.ocpp.publish.mapper.sys.IMenuMapper;
import com.zxyt.ocpp.publish.service.sys.IMenuService;
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
@Service("menuService")
public class MenuServiceImpl extends AbstractService<Menu> implements IMenuService {

    @Autowired
    private IMenuMapper menuMapper;

    @Override
    public PageInfo<Menu> selectAll(Map<String, Object> map) {
        MybatisPage.getPageSize(map);
        PageHelper.startPage(MybatisPage.page, MybatisPage.limit);
        List<Menu> areaList = this.menuMapper.findAll(map);
        return new PageInfo<>(areaList);
    }

}
