package com.zxyt.ocpp.publish.service.sys;

import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.universal.IBaseService;
import com.zxyt.ocpp.publish.entity.sys.ChannelConfig;

import java.util.List;
import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 渠道手段管理接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IChannelConfigService extends IBaseService<ChannelConfig> {

    PageInfo<ChannelConfig> selectAll(Map<String, Object> map);

    int insert(Map<String, Object> map);

    List<ChannelConfig> selectByType(Map<String, Object> map);
}
