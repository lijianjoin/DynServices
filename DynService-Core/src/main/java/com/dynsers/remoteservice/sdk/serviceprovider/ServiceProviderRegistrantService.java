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
