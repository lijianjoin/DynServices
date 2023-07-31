package com.dynsers.demo.remoteservice.spring.services;

import com.dynsers.demo.dynservice.demoapi.api.TestInterface;
import com.dynsers.remoteservice.sdk.annotations.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("RemoteServiceTest")
public class TestService {

    private static Logger logger = LoggerFactory.getLogger(TestService.class);

    @RemoteService(groupId = "${remoteService.services.groupId}",
            resourceId = "${remoteService.services.resourceId}",
            resourceVersion = "${remoteService.services.resourceVersion}",
            serviceVersion = "${remoteService.services.serviceVersion}",
            uuid = "${remoteService.services.serviceVersion}")
    private TestInterface testInterface;


    public void test (){
        String s = testInterface.getSampleString();
        logger.debug(s);
    }
}
