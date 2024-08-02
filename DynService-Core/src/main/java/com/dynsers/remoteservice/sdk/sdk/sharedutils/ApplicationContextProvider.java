package com.dynsers.remoteservice.sdk.sdk.sharedutils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * This class provides the ApplicationContext to other classes. It is used to get the ApplicationContext in classes that are not managed by Spring.
 *
 * <p>
 * IMPORTANT: This class should not be used in classes that are managed by Spring. Instead, the ApplicationContext should be injected via the constructor. All
 * new classes MUST be managed by spring, this class is only used in legacy code.
 * </p>
 *
 * @deprecated This class is only used in legacy code. All new classes MUST be managed by spring. Should be removed if all classes managed by spring.
 * This class will be improved/deleted later.
 */
@Component
@Deprecated
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * This method is called from the ApplicationContext when the bean is created. SuppressWarnings because "Make the enclosing method "static" or remove this
     * set" is not possible here.
     *
     * @param applicationContext The ApplicationContext that is being set.
     * @throws BeansException If the bean cannot be created.
     */
    @Override
    @SuppressWarnings("java:S2696")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}