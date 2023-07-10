package com.dynsers.remoteservice.sdk.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class RemoteServiceId implements Serializable {

    public RemoteServiceId() {

    }

    public RemoteServiceId(RemoteServiceId copy) {
        this.setUuid(copy.getUuid());
        this.setGroupId(copy.getGroupId());
        this.setResourceId(copy.getResourceId());
        this.setServiceId(copy.getServiceId());
        this.setResourceVersion(copy.getResourceVersion());
        this.setServiceVersion(copy.getServiceVersion());
        this.setAdditionalInfo(copy.getAdditionalInfo());
        this.setDetectInterval(copy.getDetectInterval());
    }

    public void update(RemoteServiceId copy) {
        this.setUuid(copy.getUuid());
        this.setGroupId(copy.getGroupId());
        this.setResourceId(copy.getResourceId());
        this.setServiceId(copy.getServiceId());
        this.setResourceVersion(copy.getResourceVersion());
        this.setServiceVersion(copy.getServiceVersion());
        this.setAdditionalInfo(copy.getAdditionalInfo());
        this.setDetectInterval(copy.getDetectInterval());
    }

    private String uuid = "";
    private String groupId = "";
    private String resourceId = "";
    private String uri = "";
    private String serviceId = "";
    private String resourceVersion = "";
    private String serviceVersion = "";
    private String additionalInfo = "";
    private int detectInterval = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RemoteServiceId that)) return false;

        if (getDetectInterval() != that.getDetectInterval()) return false;
        if (getUuid() != null ? !getUuid().equals(that.getUuid()) : that.getUuid() != null) return false;
        if (getGroupId() != null ? !getGroupId().equals(that.getGroupId()) : that.getGroupId() != null) return false;
        if (getResourceId() != null ? !getResourceId().equals(that.getResourceId()) : that.getResourceId() != null)
            return false;
        if (getServiceId() != null ? !getServiceId().equals(that.getServiceId()) : that.getServiceId() != null)
            return false;
        if (getResourceVersion() != null ? !getResourceVersion().equals(that.getResourceVersion()) : that.getResourceVersion() != null)
            return false;
        if (getServiceVersion() != null ? !getServiceVersion().equals(that.getServiceVersion()) : that.getServiceVersion() != null)
            return false;
        return getAdditionalInfo() != null ? getAdditionalInfo().equals(that.getAdditionalInfo()) : that.getAdditionalInfo() == null;
    }

    @Override
    public int hashCode() {
        int result = getUuid() != null ? getUuid().hashCode() : 0;
        result = 31 * result + (getGroupId() != null ? getGroupId().hashCode() : 0);
        result = 31 * result + (getResourceId() != null ? getResourceId().hashCode() : 0);
        result = 31 * result + (getUri() != null ? getUri().hashCode() : 0);
        result = 31 * result + (getServiceId() != null ? getServiceId().hashCode() : 0);
        result = 31 * result + (getResourceVersion() != null ? getResourceVersion().hashCode() : 0);
        result = 31 * result + (getServiceVersion() != null ? getServiceVersion().hashCode() : 0);
        result = 31 * result + (getAdditionalInfo() != null ? getAdditionalInfo().hashCode() : 0);
        result = 31 * result + getDetectInterval();
        return result;
    }
}
