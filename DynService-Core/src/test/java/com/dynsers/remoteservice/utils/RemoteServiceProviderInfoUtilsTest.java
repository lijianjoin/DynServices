package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RemoteServiceProviderInfoUtilsTest {

    @Test
    void getFormattedRemoteServiceId_withValidRemoteServiceId_returnsFormattedString() throws JsonProcessingException {
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

        String result = RemoteServiceProviderInfoUtils.getFormattedRemoteServiceId(serviceId);

        assertTrue(result.contains("\"groupId\" : \"groupId\""));
        assertTrue(result.contains("\"resourceId\" : \"resourceId\""));
        assertTrue(result.contains("\"resourceVersion\" : \"resourceVersion\""));
        assertTrue(result.contains("\"serviceId\" : \"serviceId\""));
        assertTrue(result.contains("\"serviceVersion\" : \"serviceVersion\""));
        assertTrue(result.contains("\"serviceName\" : \"serviceName\""));
        assertTrue(result.contains("\"uuid\" : \"uuid\""));
        assertTrue(result.contains("\"uri\" : \"uri\""));
    }
}
