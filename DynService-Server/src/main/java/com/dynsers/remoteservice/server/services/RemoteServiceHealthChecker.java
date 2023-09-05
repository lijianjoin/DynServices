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

import com.dynsers.core.data.RemoteServiceId;
import com.dynsers.core.serviceconsumer.RemoteServiceInvoker;
import com.dynsers.core.utils.RemoteServiceProviderInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Component
public class RemoteServiceHealthChecker {

    private static final Logger log = LoggerFactory.getLogger(RemoteServiceHealthChecker.class);

    @Autowired
    private RSRTransactionalService transactionalService;

    @Scheduled(fixedRate = 120000)
    public void checkStatus() throws Exception {
        List<RemoteServiceId> allServiceProviders = RegisterContainer.getServiceIdContainer().getAllServiceId();
        RemoteServiceInvoker invoker = new RemoteServiceInvoker();
        Class[] paramTypes = new Class[]{};
        Object[] paramValues = new Object[]{};
        for(RemoteServiceId ser : allServiceProviders) {
            try {
                String status = (String) invoker.invokeRemoteService(ser, "000", paramTypes, paramValues);
                System.out.println(status);
            }
            catch (ResourceAccessException accessException) {
                log.info(
                        String.format("Remote Service: %s from provider: %s cannot be accessed, will be deleted from register server",
                        ser.getServiceId(), RemoteServiceProviderInfoUtils.getFormattedRemoteServiceId(ser)));
                transactionalService.removeServiceProvider(ser);
            }
        }
    }

    // write here a oberserver pattern to check the health of the remote service


}
