package com.dynsers.remoteservice.server.services;

import com.dynsers.remoteservice.sdk.annotations.ServiceProvider;
import com.dynsers.remoteservice.sdk.enums.ServerProviderTypes;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.interfaces.RemoteServiceRegister;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceAlreadyRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ServiceProvider(version = "0.0.1", type = ServerProviderTypes.REMOTESERVICEPREGISTER,
        uuid = "4db04b3f-4903-4642-a455-fc7c8727288a")
public class RemoteServiceRegisterImpl implements RemoteServiceRegister {

    @Autowired
    private RSRTransactionalService transactionalService;

    @Override
    public RemoteServiceId getRemoteServiceId(RemoteServiceId requestServiceId) throws RSServiceNotRegisterException {
        return transactionalService.getRemoteServiceId(requestServiceId);
    }

    @Override
    public List<RemoteServiceId> getRemoteServiceIdsInOneGroupResource(RemoteServiceId requestServiceId) {
        return RegisterContainer.getServiceIdContainer().getServiceIdsInOneGroupResource(requestServiceId);
    }

    @Override
    public List<RemoteServiceId> getRemoteServiceIdsInOneGroupResourceService(RemoteServiceId requestServiceId) {
        return RegisterContainer.getServiceIdContainer().getServiceIdsInOneGroupResourceService(requestServiceId);
    }

    @Override
    public void registerServiceProvider(RemoteServiceId serviceId) throws RSServiceAlreadyRegisterException {
        transactionalService.registerServiceProvider(serviceId);
    }
}
