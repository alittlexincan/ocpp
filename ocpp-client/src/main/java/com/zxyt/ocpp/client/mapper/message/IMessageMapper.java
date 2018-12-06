package com.zxyt.ocpp.client.mapper.message;


import com.zxyt.ocpp.client.config.common.universal.IBaseMapper;
import com.zxyt.ocpp.client.entity.message.Message;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("messageMapper")
public interface IMessageMapper extends IBaseMapper<Message> {

    /**
     * 分页查询一键发布信息
     * @param map
     * @return
     */
    List<Message> findAll(Map<String, Object> map);
}
