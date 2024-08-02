/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.sdk.serviceconsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.data.RemoteServiceMethodRequest;
import com.dynsers.remoteservice.sdk.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceInvocationException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceResponseErrorException;
import com.dynsers.remoteservice.sdk.utils.SerializableConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RemoteServiceInvoker {

    public Object invokeRemoteService(RemoteServiceId serviceId, String methodName,
                                      Class<?>[] parameterTypes, Object[] args) throws Exception {
        String uri = serviceId.getUri();
        var restTemplate = new RestTemplate();

        var serialArgs = SerializableConverterUtils.convertObjectArrayToSerializableArray(args);

        var remoteServiceRequest = new RemoteServiceMethodRequest(serviceId.getServiceId(), methodName, serviceId.getServiceVersion(),
                serviceId.getServiceName(), serviceId.getUuid(), parameterTypes, serialArgs);
        log.debug(remoteServiceRequest.toString());
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var objectMapper = new ObjectMapper();
        var jsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(remoteServiceRequest);

        RemoteServiceMethodResponse resBody = restTemplate.postForObject(uri, remoteServiceRequest, RemoteServiceMethodResponse.class);
        if (null == resBody) {
            throw new RemoteServiceResponseErrorException(jsString);
        }
        if (null != resBody.getException()) {
            log.error(resBody.toString());
            Exception rsException = resBody.getExceptionType().cast(resBody.getException());
            if (RemoteServiceInvocationException.class.isAssignableFrom(rsException.getClass())) {
                rsException = (Exception) ((RemoteServiceInvocationException) rsException).getOriginalException();
            }
            log.debug("Remote Service Exception: " + rsException.getMessage());
            throw rsException;
        }
        return resBody.getResult();
    }


}
