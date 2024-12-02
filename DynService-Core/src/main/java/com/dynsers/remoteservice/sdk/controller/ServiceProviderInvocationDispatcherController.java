/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.sdk.controller;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.data.RemoteServiceMethodRequest;
import com.dynsers.remoteservice.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.exceptions.RemoteServiceInvocationException;
import com.dynsers.remoteservice.exceptions.RemoteServiceRequestErrorException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.configuration.RemoteServiceProviderProperties;
import com.dynsers.remoteservice.sdk.serviceprovider.ServiceProviderControlMethodProcessor;
import com.dynsers.remoteservice.sdk.serviceprovider.ServiceProviderReserveMethods;
import com.dynsers.remoteservice.sdk.serviceprovider.ServiceProviderContainer;
import com.dynsers.remoteservice.utils.RemoteServiceExceptionUtils;
import com.dynsers.remoteservice.utils.RemoteServiceRequestHelper;
import com.dynsers.remoteservice.utils.RemoteServiceResponseHelper;
import com.dynsers.remoteservice.utils.SerializableConverterUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Controller
@RequestMapping(
        "/${remoteService.serviceProvider.context-path}/${remoteService.serviceProvider.resourceId}/${remoteService.serviceProvider.resourceVersion}/")
@Slf4j
public class ServiceProviderInvocationDispatcherController {

    @Autowired
    private ServiceProviderControlMethodProcessor controlMethodProcessor;

    @Autowired
    private RemoteServiceProviderProperties providerProperties;

    public ServiceProviderInvocationDispatcherController() {
        log.debug("ServiceProviderInvocationDispatcherController created");
        log.debug("Create Endpoint at: " + "/${remoteSerivce.serviceProvider.context-path}/"
                + "${remoteSerivce.serviceProvider.resourceId}/"
                + "${remoteSerivce.serviceProvider.resourceVersion}/");
    }

    @PostMapping(value = "**", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> invokeServiceProvider(@RequestBody RemoteServiceMethodRequest body)
            throws ClassNotFoundException, JsonProcessingException {
        Class<?> serviceProviderClass = Class.forName(body.getServiceId());
        ResponseEntity<String> res = null;
        Method m;
        try {
            m = getMethod(body, serviceProviderClass);
        } catch (RemoteServiceRequestErrorException exception) {
            return RemoteServiceResponseHelper.createResponseEntityWithException(
                    null, exception, HttpStatus.BAD_REQUEST);
        }
        if (null == m) {
            return controlMethodProcessor.processSpecialMethod(body.getMethod(), serviceProviderClass);
        }
        Object methodResult = null;
        try {
            var serviceId = new RemoteServiceId();
            serviceId
                    .setGroupId(providerProperties.getGroupId())
                    .setResourceVersion(providerProperties.getResourceVersion())
                    .setResourceId(providerProperties.getResourceId())
                    .setServiceLocation(providerProperties.getServiceLocation())
                    .setServiceId(body.getServiceId())
                    .setServiceVersion(body.getServiceVersion())
                    .setServiceName(body.getServiceName());
            Object serviceProvider = ServiceProviderContainer.getServiceProvider(serviceId);
            methodResult = m.invoke(serviceProvider, body.getParameterValues());
        } catch (IllegalAccessException illegalAccessException) {
            res = RemoteServiceResponseHelper.createResponseEntityWithException(
                    null, new RemoteServiceInvocationException(illegalAccessException), HttpStatus.FORBIDDEN);
        } catch (InvocationTargetException invocationTargetException) {
            var exception = RemoteServiceExceptionUtils.getConcreteRemoteServiceException(invocationTargetException);
            res = RemoteServiceResponseHelper.createResponseEntityWithException(null, exception, HttpStatus.OK);
        } catch (RemoteServiceServiceNotRegisterException notRegisterException) {
            res = RemoteServiceResponseHelper.createResponseEntityWithException(
                    null, new RemoteServiceInvocationException(notRegisterException), HttpStatus.OK);
        }
        if (res != null) {
            return res;
        }
        var responseBody = new RemoteServiceMethodResponse();
        responseBody.setResult(SerializableConverterUtils.convertObjectToSerializable(methodResult));
        var json = new ObjectMapper().writeValueAsString(responseBody);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    private Method getMethod(RemoteServiceMethodRequest body, Class<?> inter)
            throws RemoteServiceRequestErrorException {
        Method result;
        if (body == null) {
            throw new RemoteServiceRequestErrorException("Error: request is null");
        } else if (StringUtils.isEmpty(body.getMethod())) {
            throw new RemoteServiceRequestErrorException("Error: Method name is empty");
        } else {
            try {
                result = inter.getMethod(body.getMethod(), body.getParameterTypes());
            } catch (NoSuchMethodException e) {
                if (ServiceProviderReserveMethods.isReserveMethod(body.getMethod())) {
                    result = null;
                } else {
                    final var paramNames =
                            RemoteServiceRequestHelper.getParamterTypesAsString(body.getParameterTypes());
                    throw new RemoteServiceRequestErrorException(String.format(
                            "Error: No such method: %s with parameters: %s", body.getMethod(), paramNames));
                }
            }
        }
        return result;
    }
}
