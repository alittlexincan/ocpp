package com.zxyt.ocpp.client.mapper.message;


import com.zxyt.ocpp.client.config.common.universal.IBaseMapper;
import com.zxyt.ocpp.client.entity.message.MessageUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("messageUserMapper")
public interface IMessageUserMapper extends IBaseMapper<MessageUser> {

    int insertBatch(List<MessageUser> list);

    List<MessageUser> selectByMessageId(Map<String, Object> map);

}
