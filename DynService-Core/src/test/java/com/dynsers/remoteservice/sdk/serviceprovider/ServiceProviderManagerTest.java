package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.annotations.ServiceProvider;
import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.interfaces.RemoteServiceRegistry;
import com.dynsers.remoteservice.sdk.configuration.RemoteServicePropertyResolver;
import com.dynsers.remoteservice.sdk.configuration.RemoteServiceProviderProperties;
import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceProxy;
import com.dynsers.remoteservice.sdk.sharedutils.SpringContextUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class ServiceProviderManagerTest {

    private MockedStatic<RemoteServiceProxy> staticRemoteServiceProxy;

    private MockedStatic<RemoteServicePropertyResolver> staticRemoteServicePropertyResolver;

    private MockedStatic<SpringContextUtils> staticSpringContextUtils;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private RemoteServiceProviderProperties providerProperties;

    @Mock
    private RemoteServiceRegistry remoteServiceRegistry;

    @Mock
    private ServiceProviderRegistrantService serviceProviderRegistrantService;

    @InjectMocks
    private ServiceProviderManager serviceProviderManager;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        staticRemoteServiceProxy = mockStatic(RemoteServiceProxy.class);
        staticRemoteServicePropertyResolver = mockStatic(RemoteServicePropertyResolver.class);
        staticSpringContextUtils = mockStatic(SpringContextUtils.class);
        doNothing().when(RemoteServiceProxy.class);
        RemoteServiceProxy.setField(any(), any());
        closeable = MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(serviceProviderManager, "remoteServiceRegister", remoteServiceRegistry);
    }

    @AfterEach
    void end() throws Exception {
        staticRemoteServiceProxy.close();
        staticRemoteServicePropertyResolver.close();
        closeable.close();
    }

    @Test
    void scanAndRegisterServiceProviders_withValidProviders_registersSuccessfully() {
        staticSpringContextUtils.when(() -> SpringContextUtils.getContext()).thenReturn(applicationContext);
        Map<String, Object> tempRes = new HashMap<>();
        tempRes.put("bean1", new TestServiceProvider());
        tempRes.put("bean2", new TestServiceProviderCondi());

        when(applicationContext.getBeansWithAnnotation(ServiceProvider.class)).thenReturn(tempRes);
        when(providerProperties.getGroupId()).thenReturn("groupId");
        when(providerProperties.getResourceVersion()).thenReturn("1.0");
        when(providerProperties.getResourceId()).thenReturn("resourceId");
        when(providerProperties.getServiceLocation()).thenReturn("location");

        staticRemoteServicePropertyResolver
                .when(() -> RemoteServicePropertyResolver.getPropertyValue(anyString()))
                .thenReturn("devTest");
        serviceProviderManager.scanAndRegisterServiceProviders();

        verify(serviceProviderRegistrantService, times(2)).addServiceId(any(RemoteServiceId.class));
    }
}
