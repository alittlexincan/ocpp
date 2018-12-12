package com.zxyt.ocpp.publish.config.channel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName Config
 * @Author Xincan
 * @Version 1.0
 **/

@Component
@Getter
@Setter
public class Config<T> {

    private T t;

    private  String a;

    @Bean(name = "aa")
    public Config config(){
        return new Config();
    }
}
