/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.interfaces;


import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceNotRegisterException;

import java.util.List;


/**
 * RemoteServiceRegister is the interface for the service provider to register the service to the service register.
 */
public interface RemoteServiceRegistry {
    void registerServiceProvider(RemoteServiceId serviceId) throws RemoteServiceServiceAlreadyRegisterException;

    RemoteServiceId getRemoteServiceId(RemoteServiceId requestServiceId) throws RemoteServiceServiceNotRegisterException;

    List<RemoteServiceId> getRemoteServiceIdsInOneGroupResource(RemoteServiceId requestServiceId);

    List<RemoteServiceId> getRemoteServiceIdsInOneGroupResourceService(RemoteServiceId requestServiceId);
}
