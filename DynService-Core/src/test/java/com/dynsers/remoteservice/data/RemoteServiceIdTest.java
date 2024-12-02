package com.dynsers.remoteservice.data;

import com.dynsers.remoteservice.enums.ServiceProviderLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RemoteServiceIdTest {

    @Test
    void update_withValidRemoteServiceId_updatesFieldsCorrectly() {
        RemoteServiceId original = new RemoteServiceId();
        RemoteServiceId copy = new RemoteServiceId()
                .setGroupId("groupId")
                .setResourceId("resourceId")
                .setResourceVersion("resourceVersion")
                .setServiceId("serviceId")
                .setServiceVersion("serviceVersion")
                .setServiceName("serviceName")
                .setServiceLocation("serviceLocation")
                .setUuid("uuid")
                .setAdditionalInfo("additionalInfo")
                .setDetectionInterval(10)
                .setLocation(ServiceProviderLocation.LOCAL);

        original.update(copy);

        assertEquals("groupId", original.getGroupId());
        assertEquals("resourceId", original.getResourceId());
        assertEquals("resourceVersion", original.getResourceVersion());
        assertEquals("serviceId", original.getServiceId());
        assertEquals("serviceVersion", original.getServiceVersion());
        assertEquals("serviceName", original.getServiceName());
        assertEquals("serviceLocation", original.getServiceLocation());
        assertEquals("uuid", original.getUuid());
        assertEquals("additionalInfo", original.getAdditionalInfo());
        assertEquals(10, original.getDetectionInterval());
        assertEquals(ServiceProviderLocation.LOCAL, original.getLocation());
    }

    @Test
    void update_withNullRemoteServiceId_throwsNullPointerException() {
        RemoteServiceId original = new RemoteServiceId();

        assertThrows(NullPointerException.class, () -> original.update(null));
    }

    @Test
    void constructor_withValidRemoteServiceId_copiesFieldsCorrectly() {
        RemoteServiceId copy = new RemoteServiceId()
                .setGroupId("groupId")
                .setResourceId("resourceId")
                .setResourceVersion("resourceVersion")
                .setServiceId("serviceId")
                .setServiceVersion("serviceVersion")
                .setServiceName("serviceName")
                .setServiceLocation("serviceLocation")
                .setUuid("uuid")
                .setAdditionalInfo("additionalInfo")
                .setDetectionInterval(10)
                .setLocation(ServiceProviderLocation.LOCAL);

        RemoteServiceId original = new RemoteServiceId(copy);

        assertEquals("groupId", original.getGroupId());
        assertEquals("resourceId", original.getResourceId());
        assertEquals("resourceVersion", original.getResourceVersion());
        assertEquals("serviceId", original.getServiceId());
        assertEquals("serviceVersion", original.getServiceVersion());
        assertEquals("serviceName", original.getServiceName());
        assertEquals("serviceLocation", original.getServiceLocation());
        assertEquals("uuid", original.getUuid());
        assertEquals("additionalInfo", original.getAdditionalInfo());
        assertEquals(10, original.getDetectionInterval());
        assertEquals(ServiceProviderLocation.LOCAL, original.getLocation());
    }

    @Test
    void constructor_withNullRemoteServiceId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoteServiceId(null));
    }
}
