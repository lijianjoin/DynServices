/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.server.repository;


import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;

public interface CustomerRSRServiceProviderRepo {

    RemoteServiceProviderEntity findByRemoteServiceId(RemoteServiceId serviceId);

}
