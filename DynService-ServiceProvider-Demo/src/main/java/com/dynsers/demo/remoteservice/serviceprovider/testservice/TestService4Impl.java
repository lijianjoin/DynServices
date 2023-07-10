package com.dynsers.demo.remoteservice.serviceprovider.testservice;

import org.springframework.stereotype.Component;

@Component
public class TestService4Impl implements TestService4{


    @Override
    public String testFunc4() {
        System.out.println("Teststet");
        return null;
    }
}
