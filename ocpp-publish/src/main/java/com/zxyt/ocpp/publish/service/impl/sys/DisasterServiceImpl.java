package com.zxyt.ocpp.publish.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.page.MybatisPage;
import com.zxyt.ocpp.publish.config.common.universal.AbstractService;
import com.zxyt.ocpp.publish.entity.sys.Disaster;
import com.zxyt.ocpp.publish.mapper.sys.IDisasterMapper;
import com.zxyt.ocpp.publish.service.sys.IDisasterService;
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
@Service("disasterService")
public class DisasterServiceImpl extends AbstractService<Disaster> implements IDisasterService {

    @Autowired
    private IDisasterMapper disasterMapper;

    @Override
    public PageInfo<Disaster> selectAll(Map<String, Object> map) {
        MybatisPage.getPageSize(map);
        PageHelper.startPage(MybatisPage.page, MybatisPage.limit);
        List<Disaster> areaList = this.disasterMapper.findAll(map);
        return new PageInfo<>(areaList);
    }

    @Override
    public Disaster selectById(String id){
        return this.disasterMapper.selectById(id);
    }

    /**
     * 更新策略配置，是否进行过策略配置
     * @param map
     * @return
     */
    @Override
    public int updateStrategyById(Map<String, Object> map){
        return this.disasterMapper.updateStrategyById(map);
    }

}
