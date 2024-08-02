/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.data;

import com.dynsers.remoteservice.sdk.enums.ServiceProviderLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RemoteServiceId implements Serializable {

    private String groupId = "";
    private String resourceId = "";
    private String resourceVersion = "";
    private String uuid = "";
    private String serviceId = "";
    private String serviceName = "";
    private String serviceVersion = "";
    private String uri = "";
    private ServiceProviderLocation location = ServiceProviderLocation.REMOTE;
    private String additionalInfo = "";
    private int detectionInterval = 0;




    public RemoteServiceId(RemoteServiceId copy) {
        this.setGroupId(copy.getGroupId());
        this.setResourceId(copy.getResourceId());
        this.setResourceVersion(copy.getResourceVersion());
        this.setServiceId(copy.getServiceId());
        this.setServiceVersion(copy.getServiceVersion());
        this.setServiceName(copy.getServiceName());
        this.setUuid(copy.getUuid());
        this.setAdditionalInfo(copy.getAdditionalInfo());
        this.setDetectionInterval(copy.getDetectionInterval());
        this.setLocation(copy.getLocation());
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
    }

}
