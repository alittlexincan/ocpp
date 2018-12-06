package com.zxyt.ocpp.client.config.core.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Configuration
@PropertySources({
        @PropertySource(value = {"file:D:/ocpp/config/ocpp-client.properties"},ignoreResourceNotFound=true,encoding = "UTF-8")
})
public class SystemConfig {

}
