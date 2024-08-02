package com.dynsers.remoteservice.sdk.config;

import com.dynsers.remoteservice.sdk.sdk.sharedutils.ApplicationContextProvider;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestApplicationContextProvider {

    @Test
    void testApplicationContextProvider() {
        ApplicationContextProvider provider = new ApplicationContextProvider();
        ApplicationContext context = new GenericApplicationContext();
        provider.setApplicationContext(context);
        assertEquals(context, ApplicationContextProvider.getApplicationContext());
    }
}