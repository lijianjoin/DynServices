package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.data.RemoteServiceId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRemoteServiceProviderUtils {

    @Test
    void testIsIdenticalLocation() {

        RemoteServiceId id1 = new RemoteServiceId();
        RemoteServiceId id2 = new RemoteServiceId();

        id1.setGroupId("com.sartorius");
        id1.setResourceId("SLI");
        id1.setResourceVersion("0.0.1");
        id1.setServiceId("RoutineProvider");
        id1.setServiceVersion("0.0.1");

        id2.setGroupId("com.sartorius");
        id2.setResourceId("SLI");
        id2.setResourceVersion("0.0.1");
        id2.setServiceId("RoutineEccentricity");
        id2.setServiceVersion("0.0.1");

        boolean res = RemoteServiceProviderUtils.isIdenticalLocation(id1, id2);

        assertTrue(res);
    }

    @Test
    void isIdenticalLocation_withIdenticalLocations_returnsTrue() {
        RemoteServiceId id1 = new RemoteServiceId();
        RemoteServiceId id2 = new RemoteServiceId();

        id1.setGroupId("com.sartorius");
        id1.setResourceId("SLI");
        id1.setResourceVersion("0.0.1");

        id2.setGroupId("com.sartorius");
        id2.setResourceId("SLI");
        id2.setResourceVersion("0.0.1");

        boolean result = RemoteServiceProviderUtils.isIdenticalLocation(id1, id2);

        assertTrue(result);
    }

    @Test
    void isIdenticalLocation_withDifferentGroupIds_returnsFalse() {
        RemoteServiceId id1 = new RemoteServiceId();
        RemoteServiceId id2 = new RemoteServiceId();

        id1.setGroupId("com.sartorius");
        id1.setResourceId("SLI");
        id1.setResourceVersion("0.0.1");

        id2.setGroupId("com.other");
        id2.setResourceId("SLI");
        id2.setResourceVersion("0.0.1");

        boolean result = RemoteServiceProviderUtils.isIdenticalLocation(id1, id2);

        assertFalse(result);
    }

    @Test
    void isIdenticalLocation_withDifferentResourceIds_returnsFalse() {
        RemoteServiceId id1 = new RemoteServiceId();
        RemoteServiceId id2 = new RemoteServiceId();

        id1.setGroupId("com.sartorius");
        id1.setResourceId("SLI");
        id1.setResourceVersion("0.0.1");

        id2.setGroupId("com.sartorius");
        id2.setResourceId("OTHER");
        id2.setResourceVersion("0.0.1");

        boolean result = RemoteServiceProviderUtils.isIdenticalLocation(id1, id2);

        assertFalse(result);
    }

    @Test
    void isIdenticalLocation_withDifferentResourceVersions_returnsFalse() {
        RemoteServiceId id1 = new RemoteServiceId();
        RemoteServiceId id2 = new RemoteServiceId();

        id1.setGroupId("com.sartorius");
        id1.setResourceId("SLI");
        id1.setResourceVersion("0.0.1");

        id2.setGroupId("com.sartorius");
        id2.setResourceId("SLI");
        id2.setResourceVersion("0.0.2");

        boolean result = RemoteServiceProviderUtils.isIdenticalLocation(id1, id2);

        assertFalse(result);
    }

    @Test
    void isIdenticalLocation_withNullGroupId_returnsFalse() {
        RemoteServiceId id1 = new RemoteServiceId();
        RemoteServiceId id2 = new RemoteServiceId();

        id1.setGroupId(null);
        id1.setResourceId("SLI");
        id1.setResourceVersion("0.0.1");

        id2.setGroupId("com.sartorius");
        id2.setResourceId("SLI");
        id2.setResourceVersion("0.0.1");

        boolean result = RemoteServiceProviderUtils.isIdenticalLocation(id1, id2);

        assertFalse(result);
    }

    @Test
    void isIdenticalLocation_withNullResourceId_returnsFalse() {
        RemoteServiceId id1 = new RemoteServiceId();
        RemoteServiceId id2 = new RemoteServiceId();

        id1.setGroupId("com.sartorius");
        id1.setResourceId(null);
        id1.setResourceVersion("0.0.1");

        id2.setGroupId("com.sartorius");
        id2.setResourceId("SLI");
        id2.setResourceVersion("0.0.1");

        boolean result = RemoteServiceProviderUtils.isIdenticalLocation(id1, id2);

        assertFalse(result);
    }

    @Test
    void isIdenticalLocation_withNullResourceVersion_returnsFalse() {
        RemoteServiceId id1 = new RemoteServiceId();
        RemoteServiceId id2 = new RemoteServiceId();

        id1.setGroupId("com.sartorius");
        id1.setResourceId("SLI");
        id1.setResourceVersion(null);

        id2.setGroupId("com.sartorius");
        id2.setResourceId("SLI");
        id2.setResourceVersion("0.0.1");

        boolean result = RemoteServiceProviderUtils.isIdenticalLocation(id1, id2);

        assertFalse(result);
    }
}
