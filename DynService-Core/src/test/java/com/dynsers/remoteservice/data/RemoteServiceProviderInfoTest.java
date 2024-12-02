package com.dynsers.remoteservice.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoteServiceProviderInfoTest {
    @Test
    void addRemoteServiceId_withValidRemoteServiceId_addsServiceSuccessfully() throws ClassNotFoundException {
        RemoteServiceProviderInfo providerInfo = new RemoteServiceProviderInfo();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId
                .setGroupId("group1")
                .setResourceId("resource1")
                .setResourceVersion("v1")
                .setServiceId("java.lang.String")
                .setServiceVersion("v1")
                .setUuid("uuid1");

        providerInfo.addRemoteServiceId(serviceId);

        assertNotNull(providerInfo.getProviderInfoContainer().get("group1"));
        assertNotNull(providerInfo.getProviderInfoContainer().get("group1").get("resource1_v1"));
        assertNotNull(providerInfo
                .getProviderInfoContainer()
                .get("group1")
                .get("resource1_v1")
                .get("java.lang.String_v1"));
        assertNotNull(providerInfo
                .getProviderInfoContainer()
                .get("group1")
                .get("resource1_v1")
                .get("java.lang.String_v1")
                .get("uuid1"));
        assertTrue(providerInfo
                .getProviderInfoContainer()
                .get("group1")
                .get("resource1_v1")
                .get("java.lang.String_v1")
                .get("uuid1")
                .contains("toString"));
    }

    @Test
    void addRemoteServiceId_withNullRemoteServiceId_throwsNullPointerException() {
        RemoteServiceProviderInfo providerInfo = new RemoteServiceProviderInfo();

        assertThrows(NullPointerException.class, () -> providerInfo.addRemoteServiceId(null));
    }

    @Test
    void addRemoteServiceId_withNonExistentClass_throwsClassNotFoundException() {
        RemoteServiceProviderInfo providerInfo = new RemoteServiceProviderInfo();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId
                .setGroupId("group1")
                .setResourceId("resource1")
                .setResourceVersion("v1")
                .setServiceId("non.existent.Class")
                .setServiceVersion("v1")
                .setUuid("uuid1");

        assertThrows(ClassNotFoundException.class, () -> providerInfo.addRemoteServiceId(serviceId));
    }

    @Test
    void addRemoteServiceId_withDuplicateServiceId_updatesServiceMethods() throws ClassNotFoundException {
        RemoteServiceProviderInfo providerInfo = new RemoteServiceProviderInfo();
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId
                .setGroupId("group1")
                .setResourceId("resource1")
                .setResourceVersion("v1")
                .setServiceId("java.lang.String")
                .setServiceVersion("v1")
                .setUuid("uuid1");

        providerInfo.addRemoteServiceId(serviceId);
        providerInfo.addRemoteServiceId(serviceId);

        assertEquals(
                1,
                providerInfo
                        .getProviderInfoContainer()
                        .get("group1")
                        .get("resource1_v1")
                        .get("java.lang.String_v1")
                        .size());
        assertTrue(providerInfo
                .getProviderInfoContainer()
                .get("group1")
                .get("resource1_v1")
                .get("java.lang.String_v1")
                .get("uuid1")
                .contains("toString"));
    }
}
