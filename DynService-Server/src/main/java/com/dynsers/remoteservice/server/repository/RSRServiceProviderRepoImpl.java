/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.dynsers.remoteservice.server.repository;

import com.dynsers.core.data.RemoteServiceId;
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
