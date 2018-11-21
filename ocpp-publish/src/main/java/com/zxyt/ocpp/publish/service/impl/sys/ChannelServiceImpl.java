package com.zxyt.ocpp.publish.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.page.MybatisPage;
import com.zxyt.ocpp.publish.config.common.universal.AbstractService;
import com.zxyt.ocpp.publish.entity.sys.Channel;
import com.zxyt.ocpp.publish.mapper.sys.IChannelMapper;
import com.zxyt.ocpp.publish.service.sys.IChannelService;
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
@Service("channelService")
public class ChannelServiceImpl extends AbstractService<Channel> implements IChannelService {

    @Autowired
    private IChannelMapper channelMapper;

    @Override
    public PageInfo<Channel> selectAll(Map<String, Object> map) {
        MybatisPage.getPageSize(map);
        PageHelper.startPage(MybatisPage.page, MybatisPage.limit);
        List<Channel> areaList = this.channelMapper.findAll(map);
        return new PageInfo<>(areaList);
    }

    @Override
    public Channel selectById(String id){
        return this.channelMapper.selectById(id);
    }

    @Override
    public List<Channel> selectByParam(Map<String, Object> map){
        return  this.channelMapper.selectByParam(map);
    }

    @Override
    public int updateStatus(Map<String, Object> map){
        return  this.channelMapper.updateStatus(map);
    }

}
