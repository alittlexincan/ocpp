package com.zxyt.ocpp.publish.service.sys;

import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.universal.IBaseService;
import com.zxyt.ocpp.publish.entity.sys.Channel;

import java.util.List;
import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 渠道手段管理接口层
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
public interface IChannelService extends IBaseService<Channel> {

    PageInfo<Channel> selectAll(Map<String, Object> map);

    Channel selectById(String id);

    List<Channel> selectByParam(Map<String, Object> map);

    /**
     * 修改渠道是否启用禁用
     * @param map
     * @return
     */
    int updateStatus(Map<String, Object> map);

}
