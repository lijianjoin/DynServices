/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.server.services;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceInvoker;
import com.dynsers.remoteservice.utils.RemoteServiceProviderInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Component
@Slf4j
public class RemoteServiceHealthChecker {

    private final RSRTransactionalService transactionalService;

    public RemoteServiceHealthChecker(RSRTransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    @Scheduled(fixedRate = 120000)
    public void checkStatus() throws Exception {
        List<RemoteServiceId> allServiceProviders =
                RegisterContainer.getServiceIdContainer().getAllServiceId();
        var invoker = new RemoteServiceInvoker();
        var paramTypes = new Class[] {};
        var paramValues = new Object[] {};
        for (RemoteServiceId ser : allServiceProviders) {
            try {
                String status = (String) invoker.invokeRemoteService(ser, "000", paramTypes, paramValues);
                log.debug(status);
            } catch (ResourceAccessException accessException) {
                log.warn(String.format(
                        "Remote Service: %s from provider: %s cannot be accessed, will be deleted from register server",
                        ser.getServiceId(), RemoteServiceProviderInfoUtils.getFormattedRemoteServiceId(ser)));
                transactionalService.removeServiceProvider(ser);
            }
        }
    }
}
