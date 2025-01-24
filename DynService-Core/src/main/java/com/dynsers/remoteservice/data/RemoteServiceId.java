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

package com.dynsers.remoteservice.data;

import com.dynsers.remoteservice.enums.ServiceProviderLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RemoteServiceId implements Serializable {

    private Integer id;
    private String groupId = "";
    private String resourceId = "";
    private String resourceVersion = "";
    private String uuid = "";
    private String serviceId = "";
    private String serviceName = "";
    private String serviceVersion = "";
    private String serviceLocation = "";
    private String uri = "";
    private ServiceProviderLocation location = ServiceProviderLocation.REMOTE;
    private String additionalInfo = "";
    private int detectionInterval = 0;

    public RemoteServiceId(RemoteServiceId copy) {
        this.setGroupId(copy.getGroupId())
                .setResourceId(copy.getResourceId())
                .setResourceVersion(copy.getResourceVersion())
                .setServiceId(copy.getServiceId())
                .setServiceVersion(copy.getServiceVersion())
                .setServiceName(copy.getServiceName())
                .setServiceLocation(copy.getServiceLocation())
                .setUuid(copy.getUuid())
                .setAdditionalInfo(copy.getAdditionalInfo())
                .setDetectionInterval(copy.getDetectionInterval())
                .setLocation(copy.getLocation());
    }

    public void update(RemoteServiceId copy) {
        this.setUuid(copy.getUuid());
        this.setGroupId(copy.getGroupId());
        this.setResourceId(copy.getResourceId());
        this.setResourceVersion(copy.getResourceVersion());
        this.setServiceId(copy.getServiceId());
        this.setServiceVersion(copy.getServiceVersion());
        this.setServiceName(copy.getServiceName());
        this.setAdditionalInfo(copy.getAdditionalInfo());
        this.setDetectionInterval(copy.getDetectionInterval());
        this.setLocation(copy.getLocation());
        this.setServiceLocation(copy.getServiceLocation());
    }
}
