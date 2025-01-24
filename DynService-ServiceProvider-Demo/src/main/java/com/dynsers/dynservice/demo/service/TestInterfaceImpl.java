package com.dynsers.dynservice.demo.service;

import com.dynsers.demo.dynservice.demoapi.api.TestInterface;
import com.dynsers.demo.dynservice.demoapi.api.UnknowParameterException;
import com.dynsers.remoteservice.annotations.ServiceProvider;
import org.springframework.stereotype.Service;


@Service
@ServiceProvider(serviceName = "TestInterfaceImpl", version = "1.0.0", uuid = "c6dadd03-a506-4d68-8c88-ad99baec47e5")
public class TestInterfaceImpl implements TestInterface {


    @Override
    public String getSampleString() {
        return "Here is the string from the remote service";
    }

    @Override
    public String getSampleStringWithExcept() throws UnknowParameterException {
        return "Here is the string from the remote service maybe the exception generated";
    }
}
