/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dynsers.remoteservice.sdk.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class RemoteServiceProperties {

    private static RemoteServiceProperties instance;
    @Value("${remoteService.serviceProvider.groupId}")
    private String serviceProviderGroupId;
    @Value("${remoteService.serviceProvider.resourceId}")
    private String serviceProviderResourceId;
    @Value("${remoteService.serviceProvider.resourceVersion}")
    private String serviceProviderResourceVersion;
    @Value("${remoteService.serviceProvider.context-path}")
    private String serviceProviderContextPath;
    @Value("${remoteService.serviceProvider.hostname}")
    private String serviceProviderHostname;
    @Value("${remoteService.server.url}")
    private String serverUrl;
    @Value("${remoteService.server.groupId}")
    private String serverGroupId;
    @Value("${remoteService.server.resourceId}")
    private String serverResourceId;
    @Value("${remoteService.server.resourceVersion}")
    private String serverResourceVersion;
    @Value("${remoteService.server.serviceVersion}")
    private String serverServiceVersion;
    private Integer serviceProviderPort;

    private RemoteServiceProperties() {

    }

    public static RemoteServiceProperties getInstance() {
        RemoteServiceProperties localRef = instance;
        if (localRef == null) {
            synchronized (RemoteServiceProperties.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new RemoteServiceProperties();
                }
            }
        }
        return localRef;
    }

}
