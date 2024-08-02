package com.dynsers.remoteservice.sdk.sdk.sharedutils;

import org.springframework.context.ApplicationContext;

import java.util.Collection;

public class SpringContextUtils {

    private SpringContextUtils() {
    }

    private static ApplicationContext context;

    public static void setContext(ApplicationContext context) {
        SpringContextUtils.context = context;
    }

    public static <T> Collection<T> getBeans(Class<T> type) {
        return context.getBeansOfType(type).values();
    }

}