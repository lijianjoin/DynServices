package com.dynsers.demo.remoteservice.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceManager;

public class ServiceConsumer {

    public static void main(String argv[]) throws NoSuchFieldException, IllegalAccessException {
        RemoteServiceId id = new RemoteServiceId();
        id.setGroupId("com.dynsers.dynservice");
        id.setResourceId("testService");
        id.setResourceVersion("0.0.1");
        id.setServiceId("com.dynsers.demo.dynservice.demoapi.api.TestInterface");
        id.setServiceVersion("0.0.1");
        id.setUuid("c8c7b30d-5f4c-4fde-ba57-6fb75be98ffc");
        //id.setUuid("c8c7b30d-5f4c-4fde-ba57-6fb75be98ffd");

        DemoService d = new DemoService();
        RemoteServiceManager.getInstance().configRemoteService(d, "test", id);
        String s = d.getTest().getSampleString();
        System.out.println(s);

        d.getTest().getSampleStringWithExcept();
    }
}
