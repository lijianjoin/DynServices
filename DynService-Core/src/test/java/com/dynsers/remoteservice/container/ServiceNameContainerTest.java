package com.dynsers.remoteservice.container;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceNameContainerTest {

    @Test
    void storeServiceByUUID_withValidServiceId_storesServiceSuccessfully()
            throws RemoteServiceServiceAlreadyRegisterException {
        ServiceNameContainer container = new ServiceNameContainer();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setUuid("uuid1");

        container.storeServiceByUUID(serviceId);

        assertNotNull(container.getServiceId("uuid1"));
        assertEquals(serviceId, container.getServiceId("uuid1"));
    }

    @Test
    void storeServiceByUUID_withExistingServiceId_throwsServiceAlreadyRegisterException() {
        ServiceNameContainer container = new ServiceNameContainer();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setUuid("uuid1");

        container.storeServiceByUUID(serviceId);
        assertThrows(RemoteServiceServiceAlreadyRegisterException.class, () -> {
            container.storeServiceByUUID(serviceId);
        });
    }

    @Test
    void getServiceId_withValidUUID_returnsServiceId() {
        ServiceNameContainer container = new ServiceNameContainer();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setUuid("uuid1");
        container.putServiceId("uuid1", serviceId);

        RemoteServiceId result = container.getServiceId("uuid1");

        assertEquals(serviceId, result);
    }

    @Test
    void getServiceId_withNonExistingUUID_throwsServiceNotRegisterException() {
        ServiceNameContainer container = new ServiceNameContainer();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setUuid("uuid1");

        assertThrows(RemoteServiceServiceNotRegisterException.class, () -> container.getServiceId(serviceId));
    }

    @Test
    void deleteServiceId_withValidServiceId_removesServiceSuccessfully() {
        ServiceNameContainer container = new ServiceNameContainer();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setUuid("uuid1");
        container.putServiceId("uuid1", serviceId);

        RemoteServiceId result = container.deleteServiceId(serviceId);

        assertEquals(serviceId, result);
        assertNull(container.getServiceId("uuid1"));
    }

    @Test
    void getAllServiceId_returnsAllStoredServiceIds() {
        ServiceNameContainer container = new ServiceNameContainer();
        RemoteServiceId serviceId1 = new RemoteServiceId();
        serviceId1.setUuid("uuid1");
        RemoteServiceId serviceId2 = new RemoteServiceId();
        serviceId2.setUuid("uuid2");

        container.putServiceId("uuid1", serviceId1);
        container.putServiceId("uuid2", serviceId2);

        List<RemoteServiceId> result = container.getAllServiceId();

        assertEquals(2, result.size());
        assertTrue(result.contains(serviceId1));
        assertTrue(result.contains(serviceId2));
    }

    @Test
    void getServiceIdSize_returnsCorrectSize() {
        ServiceNameContainer container = new ServiceNameContainer();
        RemoteServiceId serviceId1 = new RemoteServiceId();
        serviceId1.setUuid("uuid1");
        RemoteServiceId serviceId2 = new RemoteServiceId();
        serviceId2.setUuid("uuid2");

        container.putServiceId("uuid1", serviceId1);
        container.putServiceId("uuid2", serviceId2);

        int size = container.getServiceIdSize();

        assertEquals(2, size);
    }
}
