package com.zxyt.ocpp.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OcppEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcppEurekaApplication.class, args);
    }
}
