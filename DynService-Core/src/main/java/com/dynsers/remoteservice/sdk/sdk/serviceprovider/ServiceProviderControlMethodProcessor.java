/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.sdk.serviceprovider;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.sdk.data.RemoteServiceProviderInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ServiceProviderControlMethodProcessor {

    public ResponseEntity<RemoteServiceMethodResponse> processSpecialMethod(String methodName, Class<?> inter) throws ClassNotFoundException {
        var responseBody = new RemoteServiceMethodResponse();
        switch (methodName) {
            case ServiceProviderReserveMethods.METHOD_STATUSCHECK -> responseBody.setResult("Normal");
            case ServiceProviderReserveMethods.METHOD_LISTSERVICES -> {
                var providerInfo = new RemoteServiceProviderInfo();
                Collection<RemoteServiceId> servicesIds = ServiceProviderContainer.getAllRemoteServiceIds();
                for (RemoteServiceId id : servicesIds) {
                    providerInfo.addRemoteServiceId(id);
                }
                responseBody.setResult(providerInfo);
            }
            default -> responseBody.setResult(inter.getName());
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

}
