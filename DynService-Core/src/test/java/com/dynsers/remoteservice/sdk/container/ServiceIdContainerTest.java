package com.dynsers.remoteservice.sdk.container;

import com.dynsers.remoteservice.sdk.exceptions.RSServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotFoundException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RSServiceIdUtils;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceIdContainerTest {



    @Test
    public void testStoreServiceIdWithAlreadyRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        Exception exception = assertThrows(RSServiceAlreadyRegisterException.class, () -> {
            container.storeServiceId(serviceId);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceId);
        assertEquals(excepMsg, exception.getMessage());

    }

    @Test
    public void testStoreServiceId() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId stored = container.getServiceIdWithUUID(serviceId);
        assertEquals(serviceId.getUri(), stored.getUri());
    }


    @Test
    public void testGetIdWithWrongGroupNotRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservicessss");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUuid(uuid);

        Exception exception = assertThrows(RSServiceNotRegisterException.class, () -> {
            container.getServiceIdWithUUID(serviceIdRequest);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceIdRequest);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    public void testGetIdWithWrongServiceNotRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = new RemoteServiceId();
        serviceIdRequest.setGroupId("com.dynsers.dynservice");
        serviceIdRequest.setResourceId("demo");
        serviceIdRequest.setResourceVersion("0.0.1");
        serviceIdRequest.setServiceId("com.dynsers.dynservice.demo.service.TestServiceddd");
        serviceIdRequest.setServiceVersion("0.0.1");
        serviceIdRequest.setUuid(uuid);

        Exception exception = assertThrows(RSServiceNotRegisterException.class, () -> {
            container.getServiceIdWithUUID(serviceIdRequest);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceIdRequest);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    public void testGetIdWithWrongUUIDNotRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = new RemoteServiceId();
        serviceIdRequest.setGroupId("com.dynsers.dynservice");
        serviceIdRequest.setResourceId("demo");
        serviceIdRequest.setResourceVersion("0.0.1");
        serviceIdRequest.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceIdRequest.setServiceVersion("0.0.1");
        serviceIdRequest.setUuid("uuid");

        Exception exception = assertThrows(RSServiceNotFoundException.class, () -> {
            container.getServiceIdWithUUID(serviceIdRequest);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceIdRequest);
        assertEquals(excepMsg, exception.getMessage());
    }
}
