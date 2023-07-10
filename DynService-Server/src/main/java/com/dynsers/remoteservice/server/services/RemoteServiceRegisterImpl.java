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
