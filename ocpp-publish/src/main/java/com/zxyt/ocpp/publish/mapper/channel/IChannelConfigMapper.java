package com.zxyt.ocpp.publish.mapper.channel;

import com.zxyt.ocpp.publish.entity.ChannelConfig;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 渠道配置信息接口访问层
 */
@Repository("channelConfigMapper")
public interface IChannelConfigMapper {

    /**
     * 查询渠道配置信息
     * @param map
     * @return
     */
    ChannelConfig selectChannelConfig(Map<String, Object> map);

}
