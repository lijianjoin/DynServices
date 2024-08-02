package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import org.junit.jupiter.api.Test;

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
}
