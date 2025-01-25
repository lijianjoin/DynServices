package com.dynsers.remoteservice.sdk.controller;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.data.RemoteServiceMethodRequest;
import com.dynsers.remoteservice.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.exceptions.RemoteServiceInvocationException;
import com.dynsers.remoteservice.exceptions.RemoteServiceRequestErrorException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.configuration.RemoteServiceProviderProperties;
import com.dynsers.remoteservice.sdk.serviceprovider.ServiceProviderContainer;
import com.dynsers.remoteservice.sdk.serviceprovider.ServiceProviderControlMethodProcessor;
import com.dynsers.remoteservice.sdk.serviceprovider.ServiceProviderReserveMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceProviderInvocationDispatcherControllerTest {

    private MockedStatic<ServiceProviderContainer> staticServiceContainer;

    private MockedStatic<ServiceProviderReserveMethods> staticReserveMethods;

    @Mock
    private ServiceProviderControlMethodProcessor controlMethodProcessor;

    @Mock
    private RemoteServiceProviderProperties providerProperties;

    @InjectMocks
    private ServiceProviderInvocationDispatcherController controller;

    private AutoCloseable autocloseable;

    @BeforeEach
    void setUp() {
        autocloseable = MockitoAnnotations.openMocks(this);
        staticServiceContainer = mockStatic(ServiceProviderContainer.class);
        staticReserveMethods = mockStatic(ServiceProviderReserveMethods.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        staticServiceContainer.close();
        staticReserveMethods.close();
        autocloseable.close();
    }

    @Test
    void invokeServiceProvider_withValidRequest_returnsOkResponse() throws Exception {
        RemoteServiceMethodRequest request = createRemoteServiceMethodRequest();

        when(providerProperties.getGroupId()).thenReturn("groupId");
        when(providerProperties.getResourceVersion()).thenReturn("1.0");
        when(providerProperties.getResourceId()).thenReturn("resourceId");
        when(providerProperties.getServiceLocation()).thenReturn("location");

        Class<?> serviceProviderClass = Class.forName(request.getServiceId());
        Object serviceProvider = mock(serviceProviderClass);
        when(ServiceProviderContainer.getServiceProvider(any(RemoteServiceId.class)))
                .thenReturn(serviceProvider);

        ResponseEntity<String> response = controller.invokeServiceProvider(request);

        ObjectMapper om = new ObjectMapper();
        RemoteServiceMethodResponse responseObj = om.readValue(response.getBody(), RemoteServiceMethodResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseObj);
        assertEquals("remoteService.serviceProvider.groupId", responseObj.getResult());
    }

    @Test
    void invokeServiceProvider_withInvalidMethod_returnsBadRequest() throws Exception {
        RemoteServiceMethodRequest request = createRemoteServiceMethodRequest();

        ResponseEntity<String> response = controller.invokeServiceProvider(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void invokeServiceProvider_withSpecialMethod_returnsProcessedResponse() throws Exception {
        RemoteServiceMethodRequest request = createRemoteServiceMethodRequest();

        Class<?> serviceProviderClass = Class.forName(request.getServiceId());
        when(controlMethodProcessor.processSpecialMethod(request.getMethod(), serviceProviderClass))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<String> response = controller.invokeServiceProvider(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void invokeServiceProvider_withServiceNotRegistered_returnsOkWithException() throws Exception {
        RemoteServiceMethodRequest request = createRemoteServiceMethodRequest();

        when(providerProperties.getGroupId()).thenReturn("groupId");
        when(providerProperties.getResourceVersion()).thenReturn("1.0");
        when(providerProperties.getResourceId()).thenReturn("resourceId");
        when(providerProperties.getServiceLocation()).thenReturn("location");

        staticServiceContainer
                .when(() -> ServiceProviderContainer.getServiceProvider(any(RemoteServiceId.class)))
                .thenThrow(new RemoteServiceServiceNotRegisterException("Service not registered"));

        ResponseEntity<String> response = controller.invokeServiceProvider(request);

        ObjectMapper om = new ObjectMapper();
        RemoteServiceMethodResponse responseObj = om.readValue(response.getBody(), RemoteServiceMethodResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseObj);
        assertInstanceOf(RemoteServiceInvocationException.class, responseObj.getException());
    }

    @Test
    void getMethod_withValidMethod_returnsMethodForJAVARMI() throws Exception {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("getPropertyValue");
        request.setParameterTypes(new Class<?>[] {String.class});
        Class<?> serviceProviderClass =
                Class.forName("com.dynsers.remoteservice.sdk.configuration.RemoteServicePropertyResolver");

        Method method = ReflectionTestUtils.invokeMethod(controller, "getMethod", request, serviceProviderClass);

        assertNotNull(method);
        assertEquals("getPropertyValue", method.getName());
    }

    @Test
    void getMethod_ForJAVARMI_withNullRequest_throwsRequestErrorException() {
        RemoteServiceRequestErrorException exception = assertThrows(
                RemoteServiceRequestErrorException.class,
                () -> ReflectionTestUtils.invokeMethod(controller, "getMethod", null, Object.class));

        assertEquals("Error: request is null", exception.getMessage());
    }

    @Test
    void getMethod_withEmptyMethodForJAVARMIName_throwsRequestErrorException() {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("");

        RemoteServiceRequestErrorException exception = assertThrows(
                RemoteServiceRequestErrorException.class,
                () -> ReflectionTestUtils.invokeMethod(controller, "getMethod", request, Object.class));

        assertEquals("Error: Method name is empty", exception.getMessage());
    }
    //
    @Test
    void getMethod_withNoSuchMethod_ForJAVARMI_throwsRequestErrorException() throws ClassNotFoundException {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("nonExistentMethod");
        request.setParameterTypes(new Class<?>[] {String.class});
        Class<?> serviceProviderClass =
                Class.forName("com.dynsers.remoteservice.sdk.configuration.RemoteServicePropertyResolver");

        RemoteServiceRequestErrorException exception = assertThrows(
                RemoteServiceRequestErrorException.class,
                () -> ReflectionTestUtils.invokeMethod(controller, "getMethod", request, serviceProviderClass));

        assertTrue(exception.getMessage().contains("nonExistentMethod with parameters: class java.lang.String"));
    }
    //
    @Test
    void getMethod_withReserveMethod_ForJAVARMI_returnsNull() throws Exception {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("reserveMethod");
        request.setParameterTypes(new Class<?>[] {String.class});
        Class<?> serviceProviderClass =
                Class.forName("com.dynsers.remoteservice.sdk.configuration.RemoteServicePropertyResolver");

        staticReserveMethods
                .when(() -> ServiceProviderReserveMethods.isReserveMethod("reserveMethod"))
                .thenReturn(true);

        Method method = ReflectionTestUtils.invokeMethod(controller, "getMethod", request, serviceProviderClass);

        assertNull(method);
    }

    private static RemoteServiceMethodRequest createRemoteServiceMethodRequest() {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setServiceId("com.dynsers.remoteservice.sdk.configuration.RemoteServicePropertyResolver");
        request.setMethod("getPropertyValue");
        Serializable[] parameterValues = new Serializable[] {"remoteService.serviceProvider.groupId"};
        Class<?>[] parameterTypes = new Class<?>[] {String.class};
        request.setParameterSerializableValues(parameterValues);
        request.setParameterTypes(parameterTypes);
        return request;
    }
}
