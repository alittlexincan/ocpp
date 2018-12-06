package com.zxyt.ocpp.publish.config.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @ClassName ChannelConfig
 * @Description 所有渠道配置
 * @Author Xincan
 * @Version 1.0
 **/
@Slf4j
@Configuration
@PropertySources({
        @PropertySource(value = {"file:D:/ocpp/config/channel.properties"},ignoreResourceNotFound=true,encoding = "UTF-8")
})
public class ChannelConfig {
}
