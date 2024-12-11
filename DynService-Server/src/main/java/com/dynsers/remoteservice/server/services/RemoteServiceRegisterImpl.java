/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.server.services;

import com.dynsers.remoteservice.annotations.ServiceProvider;
import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.enums.ServiceProviderTypes;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.interfaces.RemoteServiceRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ServiceProvider(
        version = "0.0.1",
        type = ServiceProviderTypes.REMOTESERVICEPREGISTER,
        uuid = "4db04b3f-4903-4642-a455-fc7c8727288a",
        serviceName = "RemoteServiceRegistry")
public class RemoteServiceRegisterImpl implements RemoteServiceRegistry {

    private final RSRTransactionalService transactionalService;

    public RemoteServiceRegisterImpl(RSRTransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    @Override
    public RemoteServiceId getRemoteServiceId(RemoteServiceId requestServiceId)
            throws RemoteServiceServiceNotRegisterException {
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
    public void registerServiceProvider(RemoteServiceId serviceId) throws RemoteServiceServiceAlreadyRegisterException {
        transactionalService.registerServiceProvider(serviceId);
    }

    @Override
    public void forceRegisterServiceProvider(RemoteServiceId serviceId) {
        transactionalService.forceRegisterServiceProvider(serviceId);
    }

    @Scheduled(fixedRate = 1000)
    public void demo() throws Exception {
        System.out.println("demo");
        DemoClass demoClass = new DemoClass();
    }
}
