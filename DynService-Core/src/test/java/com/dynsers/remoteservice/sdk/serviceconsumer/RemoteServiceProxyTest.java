package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.enums.ServiceProviderLocation;
import com.dynsers.remoteservice.sdk.sdk.serviceconsumer.RemoteServiceProxy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoteServiceProxyTest {


    @Test
    void setFieldTest() throws NoSuchFieldException, IllegalAccessException {

        TestClassA a = new TestClassA();
        TestClassA b = new TestClassA();
        RemoteServiceId remoteServiceId = new RemoteServiceId
                ("groupId", "resourceId", "resourceVersion", "serviceId",
                        "serviceName", "serviceVersion", "uuid", "", ServiceProviderLocation.REMOTE, "additionalInfo",
                        10);

        Field f = a.getClass().getDeclaredField("test");
        RemoteServiceProxy.setField(a, f, remoteServiceId);

        Field t = b.getClass().getDeclaredField("test");
        RemoteServiceProxy.setField(b, t, remoteServiceId);

        TestInterface inter1 = a.getTest();
        TestInterface inter2 = b.getTest();
        assertEquals(inter1, inter2);
    }
}

class TestClassA {
    private TestInterface test;

    public TestInterface getTest() {
        return test;
    }

    public void setTest(TestInterface test) {
        this.test = test;
    }
}
