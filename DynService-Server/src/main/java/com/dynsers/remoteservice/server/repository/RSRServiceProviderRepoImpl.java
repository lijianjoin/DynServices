/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.server.repository;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSRServiceProviderRepoImpl implements CustomerRSRServiceProviderRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public RemoteServiceProviderEntity findByRemoteServiceId(RemoteServiceId serviceId) {
        RemoteServiceProviderEntity res = null;
        Query query = em.createQuery("SELECT e FROM RemoteServiceProviderEntity e " +
                        "WHERE e.groupId = :groupId " +
                        "AND e.resourceId = :resourceId " +
                        "AND e.resourceVersion = :resourceVersion " +
                        "AND e.serviceId = :serviceId " +
                        "AND e.serviceVersion = :serviceVersion " +
                        "AND e.uuid = :uuid "
                , RemoteServiceProviderEntity.class);
        query.setParameter("groupId", serviceId.getGroupId());
        query.setParameter("resourceId", serviceId.getResourceId());
        query.setParameter("resourceVersion", serviceId.getResourceVersion());
        query.setParameter("serviceId", serviceId.getServiceId());
        query.setParameter("serviceVersion", serviceId.getServiceVersion());
        query.setParameter("uuid", serviceId.getUuid());

        try {
            res = (RemoteServiceProviderEntity) query.getSingleResult();
        } catch (NoResultException e) {
            log.debug("No service found " + serviceId.toString());
        }
        return res;
    }
}
