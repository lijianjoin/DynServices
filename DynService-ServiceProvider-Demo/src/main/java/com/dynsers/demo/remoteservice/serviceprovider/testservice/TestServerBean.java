package com.dynsers.demo.remoteservice.serviceprovider.testservice;

import org.springframework.stereotype.Component;

@Component
public class TestServerBean {


    public String getTestString() {
        return "This gets from the server function";
    }
}
