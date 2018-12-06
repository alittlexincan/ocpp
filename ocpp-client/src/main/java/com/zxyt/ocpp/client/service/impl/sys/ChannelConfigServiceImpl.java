package com.zxyt.ocpp.client.service.impl.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.client.config.common.page.MybatisPage;
import com.zxyt.ocpp.client.config.common.universal.AbstractService;
import com.zxyt.ocpp.client.entity.sys.ChannelConfig;
import com.zxyt.ocpp.client.mapper.sys.IChannelConfigMapper;
import com.zxyt.ocpp.client.service.sys.IChannelConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: XincanJiang
 * @Description:
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Service("channelConfigService")
public class ChannelConfigServiceImpl extends AbstractService<ChannelConfig> implements IChannelConfigService {

    @Autowired
    private IChannelConfigMapper channelConfigMapper;

    @Override
    public PageInfo<ChannelConfig> selectAll(Map<String, Object> map) {
        MybatisPage.getPageSize(map);
        PageHelper.startPage(MybatisPage.page, MybatisPage.limit);
        List<ChannelConfig> areaList = this.channelConfigMapper.findAll(map);
        return new PageInfo<>(areaList);
    }

    @Override
    @Transactional
    public int insert(Map<String, Object> map) {
        JSONObject param = new JSONObject();
        param.put("content",new JSONObject(map).toJSONString());
        param.put("areaId", map.get("areaId"));
        param.put("organizationId", map.get("organizationId"));
        param.put("channelCode", map.get("channelCode"));
        param.put("createTime", new Date());
        ChannelConfig channelConfig = JSON.parseObject(param.toJSONString(), new TypeReference<ChannelConfig>() {});
        this.channelConfigMapper.deleteByType(map);
        return this.channelConfigMapper.insert(channelConfig);
    }

    @Override
    public List<ChannelConfig> selectByType(Map<String, Object> map) {
        return this.channelConfigMapper.findAll(map);
    }
}
