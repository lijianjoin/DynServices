package com.dynsers.remoteservice.sdk.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoteServicePropertiesTest {
    @Test
    void getInstance_withNoExistingInstance_createsNewInstance() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        assertNotNull(instance);
    }

    @Test
    void getInstance_withExistingInstance_returnsSameInstance() {
        RemoteServiceProperties instance1 = RemoteServiceProperties.getInstance();
        RemoteServiceProperties instance2 = RemoteServiceProperties.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void getServiceProviderGroupId_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServiceProviderGroupId("groupId");
        assertEquals("groupId", instance.getServiceProviderGroupId());
    }

    @Test
    void getServiceProviderResourceId_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServiceProviderResourceId("resourceId");
        assertEquals("resourceId", instance.getServiceProviderResourceId());
    }

    @Test
    void getServiceProviderResourceVersion_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServiceProviderResourceVersion("resourceVersion");
        assertEquals("resourceVersion", instance.getServiceProviderResourceVersion());
    }

    @Test
    void getServiceProviderContextPath_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServiceProviderContextPath("contextPath");
        assertEquals("contextPath", instance.getServiceProviderContextPath());
    }

    @Test
    void getServiceProviderHostname_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServiceProviderHostname("hostname");
        assertEquals("hostname", instance.getServiceProviderHostname());
    }

    @Test
    void getServerUrl_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServerUrl("serverUrl");
        assertEquals("serverUrl", instance.getServerUrl());
    }

    @Test
    void getServerGroupId_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServerGroupId("serverGroupId");
        assertEquals("serverGroupId", instance.getServerGroupId());
    }

    @Test
    void getServerResourceId_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServerResourceId("serverResourceId");
        assertEquals("serverResourceId", instance.getServerResourceId());
    }

    @Test
    void getServerResourceVersion_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServerResourceVersion("serverResourceVersion");
        assertEquals("serverResourceVersion", instance.getServerResourceVersion());
    }

    @Test
    void getServerServiceVersion_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServerServiceVersion("serverServiceVersion");
        assertEquals("serverServiceVersion", instance.getServerServiceVersion());
    }

    @Test
    void getServiceProviderPort_returnsCorrectValue() {
        RemoteServiceProperties instance = RemoteServiceProperties.getInstance();
        instance.setServiceProviderPort(8080);
        assertEquals(8080, instance.getServiceProviderPort());
    }
}
