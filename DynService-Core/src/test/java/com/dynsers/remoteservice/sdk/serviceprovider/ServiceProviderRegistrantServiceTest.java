package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.interfaces.RemoteServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceProviderRegistrantServiceTest {

    @Mock
    private RemoteServiceRegistry remoteServiceRegister;

    @InjectMocks
    private ServiceProviderRegistrantService serviceProviderRegistrantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addServiceId_addsServiceIdToList() {
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceProviderRegistrantService.addServiceId(serviceId);
        assertFalse(serviceProviderRegistrantService
                .getServiceIdRegisterStatusList()
                .isEmpty());
        assertEquals(
                serviceId,
                serviceProviderRegistrantService
                        .getServiceIdRegisterStatusList()
                        .get(0)
                        .getServiceId());
    }

    @Test
    void checkAndRegister_registersUnregisteredService() throws RemoteServiceServiceAlreadyRegisterException {
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceProviderRegistrantService.addServiceId(serviceId);

        when(remoteServiceRegister.getRemoteServiceId(serviceId))
                .thenThrow(new RemoteServiceServiceNotRegisterException("Service not registered"));
        doNothing().when(remoteServiceRegister).registerServiceProvider(serviceId);

        serviceProviderRegistrantService.checkAndRegister();

        verify(remoteServiceRegister, times(1)).forceRegisterServiceProvider(serviceId);
    }

    @Test
    void checkAndRegister_doesNotReRegisterAlreadyRegisteredService()
            throws RemoteServiceServiceAlreadyRegisterException {
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceProviderRegistrantService.addServiceId(serviceId);

        when(remoteServiceRegister.getRemoteServiceId(serviceId)).thenReturn(serviceId);

        serviceProviderRegistrantService.checkAndRegister();

        verify(remoteServiceRegister, never()).registerServiceProvider(serviceId);
    }
    //
    @Test
    void checkAndRegister_handlesExceptionDuringRegistration() throws RemoteServiceServiceAlreadyRegisterException {
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceProviderRegistrantService.addServiceId(serviceId);

        when(remoteServiceRegister.getRemoteServiceId(serviceId))
                .thenThrow(new RemoteServiceServiceNotRegisterException("Service not registered"));
        doThrow(new RuntimeException("Registration failed"))
                .when(remoteServiceRegister)
                .registerServiceProvider(serviceId);

        assertDoesNotThrow(() -> serviceProviderRegistrantService.checkAndRegister());
    }

    @Test
    void checkRemoteServiceIdRegisterStatus_returnsTrueIfServiceIsRegistered()
            throws RemoteServiceServiceNotRegisterException {
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setUri("uri");
        ServiceIdRegisterStatus status = new ServiceIdRegisterStatus(serviceId, false);

        when(remoteServiceRegister.getRemoteServiceId(serviceId)).thenReturn(serviceId);

        boolean result = ReflectionTestUtils.invokeMethod(
                serviceProviderRegistrantService, "checkRemoteServiceIdRegisterStatus", status);

        assertTrue(result);
    }

    @Test
    void checkRemoteServiceIdRegisterStatus_returnsFalseIfServiceIsNotRegistered()
            throws RemoteServiceServiceNotRegisterException {
        RemoteServiceId serviceId = new RemoteServiceId();
        ServiceIdRegisterStatus status = new ServiceIdRegisterStatus(serviceId, false);

        when(remoteServiceRegister.getRemoteServiceId(serviceId))
                .thenThrow(new RemoteServiceServiceNotRegisterException("Service not registered"));

        boolean result = ReflectionTestUtils.invokeMethod(
                serviceProviderRegistrantService, "checkRemoteServiceIdRegisterStatus", status);

        assertFalse(result);
    }
}
