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

package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.interfaces.RemoteServiceRegistry;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceAlreadyRegisterException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Slf4j
@Service
public class ServiceProviderRegistrantService {

    @Setter
    private RemoteServiceRegistry remoteServiceRegister;

    @Getter
    private final List<ServiceIdRegisterStatus> serviceIdRegisterStatusList = new LinkedList<>();

    public void addServiceId(RemoteServiceId serviceId) {
        synchronized (serviceIdRegisterStatusList) {
            serviceIdRegisterStatusList.add(new ServiceIdRegisterStatus(serviceId, false));
        }
    }

    @Scheduled(fixedDelayString = "${remoteService.server.scanInterval:600000}")
    public void checkAndRegister() {
        synchronized (serviceIdRegisterStatusList) {
            for (ServiceIdRegisterStatus serviceIdRegisterStatus : serviceIdRegisterStatusList) {
                boolean status = checkRemoteServiceIdRegisterStatus(serviceIdRegisterStatus);
                log.info("Service is registered: {}, {}", serviceIdRegisterStatus.getServiceId(), status);
                serviceIdRegisterStatus.setRegistered(status);
                if (!serviceIdRegisterStatus.isRegistered()) {
                    try {
                        log.debug("Registering service: {}", serviceIdRegisterStatus.getServiceId());
                        remoteServiceRegister.forceRegisterServiceProvider(serviceIdRegisterStatus.getServiceId());
                        serviceIdRegisterStatus.setRegistered(true);
                    } catch (RemoteServiceServiceAlreadyRegisterException e) {
                        serviceIdRegisterStatus.setRegistered(true);
                    } catch (Exception except) {
                        log.error(except.getMessage(), except);
                    }
                }
            }
        }
    }

    private boolean checkRemoteServiceIdRegisterStatus(ServiceIdRegisterStatus serviceIdRegisterStatus) {
        try {
            var found = remoteServiceRegister.getRemoteServiceId(serviceIdRegisterStatus.getServiceId());
            log.debug("Found service uri: {}", found.getUri());
            log.debug(
                    "Wait to registered service uri: {}",
                    serviceIdRegisterStatus.getServiceId().getUri());
            return StringUtils.equals(
                    found.getUri(), serviceIdRegisterStatus.getServiceId().getUri());
        } catch (RemoteServiceServiceNotRegisterException e) {
            log.info("Service not found: {}", serviceIdRegisterStatus.getServiceId());
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return false;
    }
}
