package com.dynsers.remoteservice.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceProviderTypesTest {
    @Test
    void getString_withRemoteServiceProvider_returnsProvider() {
        ServiceProviderTypes type = ServiceProviderTypes.REMOTESERVICEPROVIDER;
        assertEquals("PROVIDER", type.getString());
    }

    @Test
    void getString_withRemoteServiceRegister_returnsRegister() {
        ServiceProviderTypes type = ServiceProviderTypes.REMOTESERVICEPREGISTER;
        assertEquals("REGISTER", type.getString());
    }
}
