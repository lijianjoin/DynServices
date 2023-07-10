package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RSMethodRequest;
import com.dynsers.remoteservice.sdk.data.RSMethodResponse;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSException;
import com.dynsers.remoteservice.sdk.exceptions.RSInvocationException;
import com.dynsers.remoteservice.sdk.exceptions.RSResponseErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

public class RemoteServiceInvoker {

    Logger logger = LoggerFactory.getLogger(RemoteServiceInvoker.class);

    public Object invokeRemoteService(RemoteServiceId serviceId, Method method,
                                      Class<?>[] parameterTypes, Object[] args) throws JsonProcessingException, Exception {
        String uri = serviceId.getUri();
        RestTemplate restTemplate = new RestTemplate();
        RSMethodRequest request = new RSMethodRequest();
        request.setServiceId(serviceId.getServiceId());
        request.setServiceVersion(serviceId.getServiceVersion());
        request.setMethod(method.getName());
        request.setParameterTypes(parameterTypes);
        request.setParameterValues(args);
        logger.debug(request.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);

        HttpEntity<Object> entity = new HttpEntity<Object>(jsString, headers);
        RSMethodResponse resBody = restTemplate.postForObject(uri, request, RSMethodResponse.class);
        if(null == resBody) {
            throw new RSResponseErrorException(jsString);
        }
        if(null != resBody.getException()) {
            logger.debug(resBody.toString());
            Exception rsException = (Exception) resBody.getExceptionType().cast(resBody.getException());
            if (RSInvocationException.class.isAssignableFrom(rsException.getClass())) {
                rsException = (Exception) ((RSInvocationException) rsException).getOriginalException();
            }
            throw  rsException;
        }
        return resBody.getResult();
    }

}
