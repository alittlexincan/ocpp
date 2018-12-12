package com.zxyt.ocpp.client.service.publish;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @ClassName IPublishService
 * @Author Xincan
 * @Version 1.0
 **/
@Service("publishService")
@FeignClient(name = "OCPP-PUBLISH")
public interface IPublishService {

    /**
     * 预警调用分发平台接口
     * @param json
     */
    @PostMapping("/publish/")
    JSONObject publish(@RequestBody JSONObject json);
}
