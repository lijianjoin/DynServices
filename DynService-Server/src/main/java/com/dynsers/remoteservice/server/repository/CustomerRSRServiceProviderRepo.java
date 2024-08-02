/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.server.repository;

import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;

public interface CustomerRSRServiceProviderRepo {

    RemoteServiceProviderEntity findByRemoteServiceId(RemoteServiceId serviceId);

}
