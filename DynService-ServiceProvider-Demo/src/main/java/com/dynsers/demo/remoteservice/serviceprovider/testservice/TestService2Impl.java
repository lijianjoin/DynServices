package com.dynsers.demo.remoteservice.serviceprovider.testservice;

import org.springframework.stereotype.Component;

@Component
//@RemoteService
public class TestService2Impl implements TestService2{

    @Override
    public String testFunc() {
        System.out.println("This is the new test function");
        return null;
    }
}
