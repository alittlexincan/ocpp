package com.zxyt.ocpp.publish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OcppPublishApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcppPublishApplication.class, args);
    }
}
