package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.annotations.RemoteService;
import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.configuration.RemoteServicePropertyResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RemoteServiceServiceIdUtilsTest {

    private AutoCloseable closeable;

    private MockedStatic<RemoteServicePropertyResolver> staticRemoteServicePropertyResolver;


    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void end() throws Exception {
        closeable.close();
    }

    @Test
    void fromAnnotation_withValidAnnotation_returnsCorrectRemoteServiceId() {
        staticRemoteServicePropertyResolver = mockStatic(RemoteServicePropertyResolver.class);
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("groupId"))
                .thenReturn("groupId");
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("resourceVersion"))
                .thenReturn("resourceVersion");
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("resourceId"))
                .thenReturn("resourceId");
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("serviceVersion"))
                .thenReturn("serviceVersion");
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("serviceName"))
                .thenReturn("serviceName");
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("serviceId"))
                .thenReturn("serviceId");
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("serviceLocation"))
                .thenReturn("serviceLocation");
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("url"))
                .thenReturn("url");
        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue("uuid"))
                .thenReturn("uuid");

        RemoteService annotation = mock(RemoteService.class);
        when(annotation.groupId()).thenReturn("groupId");
        when(annotation.resourceId()).thenReturn("resourceId");
        when(annotation.resourceVersion()).thenReturn("resourceVersion");
        when(annotation.serviceVersion()).thenReturn("serviceVersion");
        when(annotation.serviceName()).thenReturn("serviceName");
        when(annotation.serviceId()).thenReturn("serviceId");
        when(annotation.serviceLocation()).thenReturn("serviceLocation");
        when(annotation.url()).thenReturn("url");
        when(annotation.uuid()).thenReturn("uuid");

        RemoteServiceId result = RemoteServiceServiceIdUtils.fromAnnotation(annotation);

        System.out.println(result);

        assertEquals("groupId", result.getGroupId());
        assertEquals("resourceId", result.getResourceId());
        assertEquals("resourceVersion", result.getResourceVersion());
        assertEquals("serviceVersion", result.getServiceVersion());
        assertEquals("serviceName", result.getServiceName());
        assertEquals("serviceId", result.getServiceId());
        assertEquals("serviceLocation", result.getServiceLocation());
        assertEquals("url", result.getUri());
        assertEquals("uuid", result.getUuid());

        staticRemoteServicePropertyResolver.close();
    }

    @Test
    void getServiceIdAsPlainString_withValidRemoteServiceId_returnsFormattedString() {
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId
                .setGroupId("groupId")
                .setResourceId("resourceId")
                .setResourceVersion("resourceVersion")
                .setServiceId("serviceId")
                .setServiceVersion("serviceVersion")
                .setServiceName("serviceName")
                .setUuid("uuid")
                .setUri("uri");

        String result = RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId);

        assertEquals(
                "Group ID: groupId, Resource ID: resourceId, Resource Version: resourceVersion, Service ID: serviceId, Service Version: serviceVersion, Service Name: serviceName, UUID: uuid, URI: uri,",
                result.trim());
    }

    @Test
    void getGroupResourceKey_withValidRemoteServiceId_returnsCorrectKey() {
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("groupId").setResourceId("resourceId").setResourceVersion("resourceVersion");

        String result = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);

        assertEquals("groupId_resourceId_resourceVersion", result);
    }

    @Test
    void getServiceKey_withValidRemoteServiceId_returnsCorrectKey() {
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId
                .setServiceId("serviceId")
                .setServiceName("serviceName")
                .setServiceVersion("serviceVersion")
                .setServiceLocation("serviceLocation");

        String result = RemoteServiceServiceIdUtils.getServiceKey(serviceId);

        assertEquals("serviceId_serviceName_serviceVersion_serviceLocation", result);
    }


}
