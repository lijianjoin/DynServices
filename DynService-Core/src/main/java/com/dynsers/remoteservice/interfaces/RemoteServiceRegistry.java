/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dynsers.remoteservice.interfaces;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceAlreadyRegisterException;

import java.util.List;

/**
 * RemoteServiceRegister is the interface for the service provider to register the service to the service register.
 */
public interface RemoteServiceRegistry {
    void registerServiceProvider(RemoteServiceId serviceId) throws RemoteServiceServiceAlreadyRegisterException;

    void forceRegisterServiceProvider(RemoteServiceId serviceId);

    RemoteServiceId getRemoteServiceId(RemoteServiceId requestServiceId)
            throws RemoteServiceServiceNotRegisterException;

    List<RemoteServiceId> getRemoteServiceIdsInOneGroupResource(RemoteServiceId requestServiceId);

    List<RemoteServiceId> getRemoteServiceIdsInOneGroupResourceService(RemoteServiceId requestServiceId);
}
