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
import com.dynsers.remoteservice.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.data.RemoteServiceProviderInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ServiceProviderControlMethodProcessor {

    public ResponseEntity<String> processSpecialMethod(String methodName, Class<?> inter)
            throws ClassNotFoundException, JsonProcessingException {
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
        var json = new ObjectMapper().writeValueAsString(responseBody);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
