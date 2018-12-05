package com.zxyt.ocpp.publish.mapper.message;


import com.zxyt.ocpp.publish.config.common.universal.IBaseMapper;
import com.zxyt.ocpp.publish.entity.message.MessageUser;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("messageUserMapper")
public interface IMessageUserMapper extends IBaseMapper<MessageUser> {

    int insertBatch(List<MessageUser> list);

}
