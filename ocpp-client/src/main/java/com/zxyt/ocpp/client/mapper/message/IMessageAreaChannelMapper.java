package com.zxyt.ocpp.client.mapper.message;


import com.zxyt.ocpp.client.config.common.universal.IBaseMapper;
import com.zxyt.ocpp.client.entity.message.MessageAreaChannel;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("messageAreaChannelMapper")
public interface IMessageAreaChannelMapper extends IBaseMapper<MessageAreaChannel> {

    int insertBatch(List<MessageAreaChannel> list);

}
