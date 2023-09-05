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

package com.dynsers.core.configuration;

import com.dynsers.core.annotations.ConfigValue;
import com.dynsers.core.parameterinject.ConfigValueParamterInject;
import lombok.Data;

@Data
public class RSProperties {

    @ConfigValue("${remoteService.serviceProvider.groupId}")
    private String serviceProviderGroupId;

    @ConfigValue("${remoteService.serviceProvider.resourceId}")
    private String serviceProviderResourceId;

    @ConfigValue("${remoteService.serviceProvider.resourceVersion}")
    private String serviceProviderResourceVersion;

    @ConfigValue("${remoteService.serviceProvider.context-path}")
    private String serviceProviderContextPath;

    @ConfigValue("${remoteService.serviceProvider.hostname}")
    private String serviceProviderHostname;

    @ConfigValue("${remoteService.server.url}")
    private String serverUrl;

    @ConfigValue("${remoteService.server.groupId}")
    private String serverGroupId;

    @ConfigValue("${remoteService.server.resourceId}")
    private String serverResourceId;

    @ConfigValue("${remoteService.server.resourceVersion}")
    private String serverResourceVersion;

    @ConfigValue("${remoteService.server.serviceVersion}")
    private String serverServiceVersion;

    private int serviceProviderPort;


    private static RSProperties instance;

    private RSProperties() {

    }

    public static RSProperties getInstance() {
        RSProperties localRef = instance;
        if (localRef == null) {
            synchronized (RSProperties.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new RSProperties();
                    try {
                        ConfigValueParamterInject.injectConfigValue(instance);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return localRef;
    }

}
