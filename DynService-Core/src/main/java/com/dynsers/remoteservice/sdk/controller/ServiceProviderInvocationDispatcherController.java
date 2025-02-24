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

package com.dynsers.remoteservice.sdk.controller;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.data.RemoteServiceMethodRequest;
import com.dynsers.remoteservice.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.enums.RequestSource;
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
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Method m = null;
        try {
            m = switch (body.getRequestSource()) {
                case RequestSource.RMI_JAVA -> getMethodForJAVARMI(body, serviceProviderClass);
                case RequestSource.REST_WEB -> getMethodForWebREST(body, serviceProviderClass);
                default -> throw new RemoteServiceRequestErrorException("Error: Request source is not supported");
            };
        } catch (RemoteServiceRequestErrorException exception) {
            return RemoteServiceResponseHelper.createResponseEntityWithException(
                    null,body.getRequestSource(), exception, HttpStatus.BAD_REQUEST);
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
            methodResult = switch (body.getRequestSource()) {
                case RequestSource.RMI_JAVA -> m.invoke(serviceProvider, body.getParameterSerializableValues());
                case RequestSource.REST_WEB -> invokeMethodForWebREST(m, serviceProvider, body);
                default -> throw new RemoteServiceRequestErrorException("Error: Request source is not supported");
            };
        } catch (IllegalAccessException illegalAccessException) {
            res = RemoteServiceResponseHelper.createResponseEntityWithException(
                    null, body.getRequestSource(), new RemoteServiceInvocationException(illegalAccessException), HttpStatus.FORBIDDEN);
        } catch (InvocationTargetException invocationTargetException) {
            var exception = RemoteServiceExceptionUtils.getConcreteRemoteServiceException(invocationTargetException);
            res = RemoteServiceResponseHelper.createResponseEntityWithException(null, body.getRequestSource(), exception, HttpStatus.valueOf(503));
        } catch (RemoteServiceServiceNotRegisterException notRegisterException) {
            res = RemoteServiceResponseHelper.createResponseEntityWithException(
                    null, body.getRequestSource(), new RemoteServiceInvocationException(notRegisterException), HttpStatus.valueOf(503));
        }
        if (res != null) {
            return res;
        }
        return switch (body.getRequestSource()) {
            case RequestSource.RMI_JAVA -> createResponseEntityForJavaRMI(methodResult);
            case RequestSource.REST_WEB -> createResponseEntityForWebREST(methodResult);
            default -> throw new RemoteServiceRequestErrorException("Error: Request source is not supported");
        };
    }


    private ResponseEntity<String> createResponseEntityForWebREST(Object methodResult) throws JsonProcessingException {
        var json = new ObjectMapper().writeValueAsString(methodResult);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    private ResponseEntity<String> createResponseEntityForJavaRMI(Object methodResult) throws JsonProcessingException {
        var responseBody = new RemoteServiceMethodResponse();
        responseBody.setResult(SerializableConverterUtils.convertObjectToSerializable(methodResult));
        var json = new ObjectMapper().writeValueAsString(responseBody);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    private Object invokeMethodForWebREST(Method method, Object serviceProvider, RemoteServiceMethodRequest body)
            throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
        Class<?>[] parameterClasses = method.getParameterTypes();
        ObjectMapper mapper = new ObjectMapper();
        int pos = 0;
        Object[] parameters = new Object[parameterClasses.length];
        if(body.getParameterRawValues() != null) {
            for(Object objValue : body.getParameterRawValues()) {
                Class<?> cls = parameterClasses[pos];
                TreeNode node = mapper.valueToTree(objValue);
                Object obj = mapper.treeToValue(node, cls);
                parameters[pos] = obj;
                pos++;
            }
        }
        return method.invoke(serviceProvider, parameters);
    }

    private Method getMethodForJAVARMI(RemoteServiceMethodRequest body, Class<?> inter)
            throws RemoteServiceRequestErrorException {
        Method result = null;
        if (body == null) {
            throw new RemoteServiceRequestErrorException("Error: request is null");
        } else if (StringUtils.isEmpty(body.getMethod())) {
            throw new RemoteServiceRequestErrorException("Error: Method name is empty");
        } else {
            try {
                result = inter.getMethod(body.getMethod(), body.getParameterTypes());
            } catch (NoSuchMethodException e) {
                if (!ServiceProviderReserveMethods.isReserveMethod(body.getMethod())) {
                    final var paramNames =
                            RemoteServiceRequestHelper.getParamterTypesAsString(body.getParameterTypes());
                    throw new RemoteServiceRequestErrorException(String.format(
                            "Error: No such method: %s with parameters: %s", body.getMethod(), paramNames));
                }
            }
        }
        return result;
    }

    private Method getMethodForWebREST(RemoteServiceMethodRequest body, Class<?> inter)
            throws RemoteServiceRequestErrorException {
        Method result = null;
        if (body == null) {
            throw new RemoteServiceRequestErrorException("Error: request is null");
        } else if (StringUtils.isEmpty(body.getMethod())) {
            throw new RemoteServiceRequestErrorException("Error: Method name is empty");
        } else {
            try {
                Method[] methods = inter.getMethods();
                for(Method m : methods) {
                    if (m.getName().equals(body.getMethod())) {
                        // 检查是否有参数，若有，则进一步检查参数数量是否匹配
                        if (body.getParameterRawValues() != null) {
                            if (m.getParameterCount() == body.getParameterRawValues().length) {
                                result = m;
                                break;
                            }
                        } else {
                            // 没有参数时直接赋值结果
                            result = m;
                            break;
                        }
                    }
                }
                if(result == null ) {
                    throw new NoSuchMethodException();
                }
                return result;
            } catch (NoSuchMethodException e) {
                if (!ServiceProviderReserveMethods.isReserveMethod(body.getMethod())) {
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
