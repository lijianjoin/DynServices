package com.dynsers.demo.remoteservice.serviceconsumer;

import com.dynsers.demo.dynservice.demoapi.api.TestInterface;
import com.dynsers.remoteservice.sdk.annotations.RemoteService;
import lombok.Data;

@Data
public class DemoService {

    @RemoteService
    private TestInterface test;
}
