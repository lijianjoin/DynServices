package com.dynsers.remoteservice.server.repository;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import jakarta.persistence.*;

public class RSRServiceProviderRepoImpl implements CustomerRSRServiceProviderRepo{

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
        }
        catch (NoResultException e) {
        }
        return res;
    }
}
