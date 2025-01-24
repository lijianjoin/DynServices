package com.dynsers.remoteservice.serviceconsumer;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.enums.ServiceProviderLocation;
import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceProxy;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

interface TestInterface {
}

class RemoteServiceProxyTest {

    @Test
    void setFieldTest() throws NoSuchFieldException {

        TestClassA a = new TestClassA();
        TestClassA b = new TestClassA();
        RemoteServiceId remoteServiceId = new RemoteServiceId(1,
                "groupId",
                "resourceId",
                "resourceVersion",
                "serviceId",
                "serviceName",
                "serviceLocation",
                "serviceVersion",
                "uuid",
                "",
                ServiceProviderLocation.REMOTE,
                "additionalInfo",
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

@Getter
@Setter
class TestClassA {
    private TestInterface test;
}
