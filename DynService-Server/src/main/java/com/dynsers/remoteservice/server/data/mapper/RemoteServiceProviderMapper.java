package com.dynsers.remoteservice.server.data.mapper;

import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;

public class RemoteServiceProviderMapper {

    //RemoteServiceProviderMapper MAPPER = Mappers.getMapper( RemoteServiceProviderMapper.class );

    public static RemoteServiceProviderEntity toEntity(RemoteServiceId source) {
        if(source == null) {
            return null;
        }
        RemoteServiceProviderEntity res = new RemoteServiceProviderEntity();
        res.setUri(source.getUri());
        res.setServiceId(source.getServiceId());
        res.setServiceVersion(source.getServiceVersion());
        res.setGroupId(source.getGroupId());
        res.setDetectionInterval(source.getDetectInterval());
        res.setResourceId(source.getResourceId());
        res.setResourceVersion(source.getResourceVersion());
        res.setUuid(source.getUuid());
        res.setAdditionalInfo(source.getAdditionalInfo());
        return res;
    }

    public static RemoteServiceId toDTO(RemoteServiceProviderEntity source) {
        if(source == null) {
            return null;
        }
        RemoteServiceId res = new RemoteServiceId();
        res.setUri(source.getUri());
        res.setGroupId(source.getGroupId());
        res.setResourceId(source.getResourceId());
        res.setResourceVersion(source.getResourceVersion());
        res.setServiceId(source.getServiceId());
        res.setServiceVersion(source.getServiceVersion());
        res.setDetectInterval(source.getDetectionInterval());
        res.setUuid(source.getUuid());
        res.setAdditionalInfo(source.getAdditionalInfo());
        return res;
    }
}
