package com.dynsers.remoteservice.server.services;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RSServiceIdUtils;
import com.dynsers.remoteservice.server.repository.RSRServiceProviderRepo;
import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import com.dynsers.remoteservice.server.data.mapper.RemoteServiceProviderMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RSRTransactionalService {

    Logger logger = LoggerFactory.getLogger(RSRTransactionalService.class);

    @Autowired
    RSRServiceProviderRepo serviceProviderRepo;


    @PostConstruct
    public void initRemoteServiceProvider() {
        List<RemoteServiceProviderEntity> providers = serviceProviderRepo.findAll();
        providers.forEach(ent -> {
            RemoteServiceId rmsId = RemoteServiceProviderMapper.toDTO(ent);
            RegisterContainer.getServiceIdContainer().storeServiceId(rmsId);
        });
    }

    public RemoteServiceId getRemoteServiceId(RemoteServiceId requestServiceId) throws RSServiceNotRegisterException {
        return RegisterContainer.getServiceIdContainer().getServiceIdWithUUID(requestServiceId);
    }

    @Transactional
    public void registerServiceProvider(RemoteServiceId serviceId) throws RSServiceAlreadyRegisterException {

        try {
            RemoteServiceId existedId = RegisterContainer.getServiceIdContainer().getServiceIdWithUUID(serviceId);
            if(null != existedId) {
                throw new RSServiceAlreadyRegisterException(
                        RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        } catch (RSServiceNotRegisterException e) {
            logger.debug("No Service Provider found, continue to register");
        }

        RemoteServiceProviderEntity entity = RemoteServiceProviderMapper.toEntity(serviceId);
        Example<RemoteServiceProviderEntity> example = Example.of(entity);
        RemoteServiceProviderEntity actual = null;
        try {
            actual = serviceProviderRepo.findByRemoteServiceId(serviceId);
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        if (actual == null) {
            RegisterContainer.getServiceIdContainer().storeServiceId(serviceId);
            serviceProviderRepo.save(entity);
        } else {
            throw new RSServiceAlreadyRegisterException(
                    RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
        }
    }
}
