package com.dynsers.remoteservice.server.repository;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;

public interface CustomerRSRServiceProviderRepo {

    RemoteServiceProviderEntity findByRemoteServiceId(RemoteServiceId serviceId);

}
