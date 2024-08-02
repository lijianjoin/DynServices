package com.dynsers.remoteservice.sdk.config;

import com.dynsers.remoteservice.sdk.sdk.configuration.RemoteServicePropertyResolver;
import com.dynsers.remoteservice.sdk.sdk.sharedutils.ApplicationContextProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class TestRemoteServicePropertyResolver {

    private MockedStatic<ApplicationContextProvider> providerMockedStatic;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private Environment environment;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        providerMockedStatic = mockStatic(ApplicationContextProvider.class);
        providerMockedStatic.when(ApplicationContextProvider::getApplicationContext).thenReturn(applicationContext);
        when(applicationContext.getEnvironment()).thenReturn(environment);
    }

    @AfterEach
    public void tearDown() throws Exception {
        providerMockedStatic.close();
        closeable.close();
    }

    @Test
    void testGetPropertyValueWithWrongReturnValue() {
        when(environment.getProperty("testYaml.testKey", "")).thenReturn("not_equal");
        String result = RemoteServicePropertyResolver.getPropertyValue("${testYaml.testKey}");
        assertNotEquals("equal", result);
    }

    @Test
    void testGetPropertyValueWithoutPlaceholder() {
        when(environment.getProperty("testYaml.testKey", "")).thenReturn("testYaml.testKey");
        String result = RemoteServicePropertyResolver.getPropertyValue("testYaml.testKey");
        assertEquals("testYaml.testKey", result);
    }

    @Test
    void testGetPropertyValueWithPlaceholder() {
        when(environment.getProperty("testYaml.testKey", "")).thenReturn("abc 1");
        String result = RemoteServicePropertyResolver.getPropertyValue("${testYaml.testKey}");
        assertEquals("abc 1", result);
    }

}
