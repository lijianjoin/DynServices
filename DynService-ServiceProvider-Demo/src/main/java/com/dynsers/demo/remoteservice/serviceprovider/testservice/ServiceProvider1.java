package com.dynsers.demo.remoteservice.serviceprovider.testservice;

import com.dynsers.demo.dynservice.demoapi.api.TestInterface;
import com.dynsers.demo.dynservice.demoapi.api.UnknowParameterException;
import com.dynsers.remoteservice.sdk.annotations.ServiceProvider;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ServiceProvider(version = "0.0.1", uuid = "c8c7b30d-5f4c-4fde-ba57-6fb75be98ffc")
public class ServiceProvider1 implements TestInterface {
    @Override
    public String getSampleString() {
        return "Here is a sample String.";
    }

    @Override
    public String getSampleStringWithExcept() throws UnknowParameterException {
        throw new UnknowParameterException("Error was met");
    }

    public static void main(String argv[]) {
        System.out.println(String.valueOf(UUID.randomUUID()));
    }
}
