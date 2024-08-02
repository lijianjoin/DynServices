/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.server.services;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceServiceIdUtils;
import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import com.dynsers.remoteservice.server.data.mapper.RemoteServiceProviderMapper;
import com.dynsers.remoteservice.server.repository.RSRServiceProviderRepo;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RSRTransactionalService {
    private final RSRServiceProviderRepo serviceProviderRepo;

    private final RemoteServiceProviderMapper mapper = Mappers.getMapper( RemoteServiceProviderMapper.class );

    public RSRTransactionalService(RSRServiceProviderRepo serviceProviderRepo) {
        this.serviceProviderRepo = serviceProviderRepo;
    }

    @PostConstruct
    public void initRemoteServiceProvider() {
        List<RemoteServiceProviderEntity> providers = serviceProviderRepo.findAll();
        providers.forEach(ent -> {
            RemoteServiceId rmsId = mapper.toDto(ent);
            RegisterContainer.getServiceIdContainer().storeServiceId(rmsId);
        });
    }

    public RemoteServiceId getRemoteServiceId(RemoteServiceId requestServiceId) throws RemoteServiceServiceNotRegisterException {
        return RegisterContainer.getServiceIdContainer().getRemoteService(requestServiceId);
    }

    @Transactional
    public void removeServiceProvider(RemoteServiceId serviceId) throws RemoteServiceServiceAlreadyRegisterException {
        RemoteServiceProviderEntity actual = serviceProviderRepo.findByRemoteServiceId(serviceId);
        RegisterContainer.getServiceIdContainer().deleteServiceId(serviceId);
        serviceProviderRepo.delete(actual);
    }

    @Transactional
    public void registerServiceProvider(RemoteServiceId serviceId) throws RemoteServiceServiceAlreadyRegisterException {

        try {
            RemoteServiceId existedId = RegisterContainer.getServiceIdContainer().getRemoteService(serviceId);
            if (null != existedId) {
                throw new RemoteServiceServiceAlreadyRegisterException(
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        } catch (RemoteServiceServiceNotRegisterException e) {
            log.debug("No Service Provider found, continue to register");
        }
        RemoteServiceProviderEntity entity = mapper.toEntity(serviceId);
        RemoteServiceProviderEntity actual = null;
        try {
            actual = serviceProviderRepo.findByRemoteServiceId(serviceId);
        } catch (NoResultException e) {
            log.debug("No result " + e.getMessage());
        }
        if (actual == null) {
            RegisterContainer.getServiceIdContainer().storeServiceId(serviceId);
            serviceProviderRepo.save(entity);
        } else {
            throw new RemoteServiceServiceAlreadyRegisterException(
                    RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
        }
    }
}
