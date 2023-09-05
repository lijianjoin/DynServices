/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.dynsers.core.serviceprovider;

import com.dynsers.core.configuration.RSProperties;
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
@Configuration("serviceProviderStartupListener")
@ComponentScan("com.dynsers.remoteservice.sdk")
public class ServiceProviderStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    public static boolean initialized = false;

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if(!initialized) {
            RSProperties.getInstance().setServiceProviderPort(webServerAppCtxt.getWebServer().getPort());
            ServiceProviderManager.getInstance().scanAndRegisterServiceProviders(applicationReadyEvent.getApplicationContext());
            initialized = true;
        }
    }
}
