package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.sdk.configuration.RSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Author: Jian Li
 * RSSContextListener: RemoteServiceServerContextListener
 */
@Configuration
@ComponentScan("com.dynsers.remoteservice.sdk")
public class ServiceProviderStartupListener implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        RSProperties.getInstance().setServiceProviderPort(webServerAppCtxt.getWebServer().getPort());
        ServiceProviderManager.getInstance().scanAndRegisterServiceProviders(applicationReadyEvent.getApplicationContext());
    }


}
