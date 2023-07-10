package com.dynsers.demo.remoteservice.serviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dynsers.demo.remoteservice.serviceprovider"})
public class DynServiceProviderApp {

    public static void main(String[] args) {

        SpringApplication.run(DynServiceProviderApp.class, args);

    }
}
