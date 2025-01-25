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

package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.data.RemoteServiceMethodRequest;
import com.dynsers.remoteservice.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.enums.RequestSource;
import com.dynsers.remoteservice.exceptions.RemoteServiceResponseErrorException;
import com.dynsers.remoteservice.exceptions.RemoteServiceInvocationException;
import com.dynsers.remoteservice.utils.SerializableConverterUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RemoteServiceInvoker {

    public Object invokeRemoteService(
            RemoteServiceId serviceId, String methodName, Class<?>[] parameterTypes, Object[] args) throws Exception {
        String uri = serviceId.getUri();
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var serialArgs = SerializableConverterUtils.convertObjectArrayToSerializableArray(args);

        var remoteServiceRequest = new RemoteServiceMethodRequest(
                serviceId.getServiceId(),
                methodName,
                serviceId.getServiceVersion(),
                serviceId.getServiceName(),
                serviceId.getUuid(),
                RequestSource.RMI_JAVA,
                parameterTypes,
                serialArgs, null);
        log.debug(remoteServiceRequest.toString());
        var objectMapper = new ObjectMapper();

        var jsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(remoteServiceRequest);
        HttpEntity<String> request = new HttpEntity<>(jsString, headers);
        String resBody = restTemplate.postForObject(uri, request, String.class);

        if (null == resBody) {
            throw new RemoteServiceResponseErrorException(jsString);
        }
        RemoteServiceMethodResponse response = objectMapper.readValue(resBody, RemoteServiceMethodResponse.class);
        if (null != response.getException()) {
            log.error(resBody);
            Exception rsException = response.getExceptionType().cast(response.getException());
            if (RemoteServiceInvocationException.class.isAssignableFrom(rsException.getClass())) {
                rsException = (Exception) ((RemoteServiceInvocationException) rsException).getOriginalException();
            }
            log.debug("Remote Service Exception: {}", rsException.getMessage());
            throw rsException;
        }
        return response.getResult();
    }
}
