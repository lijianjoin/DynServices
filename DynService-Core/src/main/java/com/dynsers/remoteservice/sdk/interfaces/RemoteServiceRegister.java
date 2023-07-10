package com.dynsers.remoteservice.sdk.interfaces;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotRegisterException;

import java.util.List;

public interface RemoteServiceRegister {
    void registerServiceProvider(RemoteServiceId serviceId) throws RSServiceAlreadyRegisterException;
    RemoteServiceId getRemoteServiceId(RemoteServiceId requestServiceId) throws RSServiceNotRegisterException;
    List<RemoteServiceId> getRemoteServiceIdsInOneGroupResource(RemoteServiceId requestServiceId);

    List<RemoteServiceId> getRemoteServiceIdsInOneGroupResourceService(RemoteServiceId requestServiceId);
}
