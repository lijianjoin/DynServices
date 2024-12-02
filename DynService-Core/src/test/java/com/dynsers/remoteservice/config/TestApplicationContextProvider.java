package com.dynsers.remoteservice.config;

import com.dynsers.remoteservice.sdk.sharedutils.ApplicationContextProvider;
import com.dynsers.remoteservice.sdk.sharedutils.SpringContextUtils;
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
        assertEquals(context, SpringContextUtils.getContext());
    }
}