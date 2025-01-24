package com.dynsers.remoteservice.container;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceAlreadyRegisterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupServiceContainerTest {
    @Test
    void storeService_withValidServiceId_storesServiceSuccessfully() {
        GroupServiceContainer container = new GroupServiceContainer();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("group1").setResourceId("resource1").setServiceId("service1");

        container.storeService(serviceId);

        assertNotNull(container.getResourceServices("group1_resource1_"));
        assertTrue(container
                .getResourceServices("group1_resource1_")
                .getAllServiceId()
                .contains(serviceId));
    }

    @Test
    void storeService_withNullServiceId_throwsNullPointerException() {
        GroupServiceContainer container = new GroupServiceContainer();

        assertThrows(NullPointerException.class, () -> container.storeService(null));
    }

    @Test
    void storeService_throwServiceAlreadyRegisterException() {
        GroupServiceContainer container = new GroupServiceContainer();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("group1").setResourceId("resource1").setServiceId("service1");

        container.storeService(serviceId);
        assertThrows(RemoteServiceServiceAlreadyRegisterException.class, () -> container.storeService(serviceId));
    }

    @Test
    void storeService_throwServiceAlreadyRegisterException1() {
        GroupServiceContainer container = new GroupServiceContainer();
        RemoteServiceId serviceId1 = new RemoteServiceId();
        serviceId1.setGroupId("group1").setResourceId("resource1").setServiceId("service1");
        RemoteServiceId serviceId2 = new RemoteServiceId();
        serviceId2.setGroupId("group1").setResourceId("resource1").setServiceId("service2");

        container.storeService(serviceId1);
        container.storeService(serviceId2);

        assertNotNull(container.getResourceServices("group1_resource1_"));
        assertEquals(
                2,
                container
                        .getResourceServices("group1_resource1_")
                        .getAllServiceId()
                        .size());
        assertTrue(container
                .getResourceServices("group1_resource1_")
                .getAllServiceId()
                .contains(serviceId1));
        assertTrue(container
                .getResourceServices("group1_resource1_")
                .getAllServiceId()
                .contains(serviceId2));
    }
}
