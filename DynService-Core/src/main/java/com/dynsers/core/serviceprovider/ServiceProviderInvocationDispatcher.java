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
package com.dynsers.core.serviceprovider;

import com.dynsers.core.data.RSMethodRequest;
import com.dynsers.core.data.RSMethodResponse;
import com.dynsers.core.data.RemoteServiceId;
import com.dynsers.core.exceptions.RSException;
import com.dynsers.core.exceptions.RSInvocationException;
import com.dynsers.core.exceptions.RSRequestErrorException;
import com.dynsers.core.utils.RSExceptionUtils;
import com.dynsers.core.utils.RSRequestHelper;
import com.dynsers.core.utils.RSResposeHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Controller
@RequestMapping("/${remoteService.serviceProvider.context-path}/${remoteService.serviceProvider.resourceId}/${remoteService.serviceProvider.resourceVersion}/")
public class ServiceProviderInvocationDispatcher {

    @Autowired()
    @Qualifier("serviceProviderStartupListener")
    private ServiceProviderStartupListener serviceConfig;

    @Autowired
    private ServiceProviderControlMethodProcessor controlMethodProcessor;

    public ServiceProviderInvocationDispatcher() {

    }

    @RequestMapping(value = "**")
    public ResponseEntity<RSMethodResponse> invokeServiceProvider(@RequestBody RSMethodRequest body, HttpServletRequest request) throws ClassNotFoundException {
        Class<?> serviceProviderClass = Class.forName(body.getServiceId());
        ResponseEntity<RSMethodResponse> res = null;
        Method m;
        try {
            m = getMethod(body, serviceProviderClass);
        }
        catch (RSRequestErrorException exception) {
            return RSResposeHelper.createResponseEntityWithException(null, exception, HttpStatus.BAD_REQUEST);
        }
        if(null == m) {
            ResponseEntity<RSMethodResponse> response =
                    controlMethodProcessor.processSpecialMethod(body.getMethod(), serviceProviderClass);
            return response;
        }
        Object methodResult = null;
        try {
            RemoteServiceId serviceId = ServiceProviderManager.getInstance().getBaseServiceId();
            serviceId.setServiceId(body.getServiceId());
            serviceId.setServiceVersion(body.getServiceVersion());
            Object serviceProvider = ServiceProviderContainer.getServiceProvider(serviceId);
            methodResult = m.invoke(serviceProvider, body.getParameterValues());
        } catch ( IllegalAccessException illegalAccessException) {
            res = RSResposeHelper.createResponseEntityWithException(null, new RSInvocationException(illegalAccessException), HttpStatus.FORBIDDEN);
        } catch ( InvocationTargetException invocationTargetException) {
            RSException exception = RSExceptionUtils.getConcreteRSException(invocationTargetException);
            res = RSResposeHelper.createResponseEntityWithException(null, exception, HttpStatus.OK);
        }
        if(res!=null)
            return res;
        RSMethodResponse responseBody = new RSMethodResponse();
        responseBody.setResult(methodResult);
        res = new ResponseEntity<>(responseBody, HttpStatus.OK);
        return res;
    }



    private Method getMethod(RSMethodRequest body, Class<?> inter) throws RSRequestErrorException{
        Method result = null;
        if (body == null) {
            throw new RSRequestErrorException("Error: request is null");
        } else if (StringUtils.isEmpty(body.getMethod())) {
            throw new RSRequestErrorException("Error: Method name is empty");
        } else {
            try {
                result = inter.getMethod(body.getMethod(), body.getParameterTypes());
            } catch (NoSuchMethodException e) {
                if(ServiceProviderReserveMethods.isReserveMethod(body.getMethod())) {
                    result = null;
                }
                else {
                    final String paramNames = RSRequestHelper.getParamterTypesAsString(body.getParameterTypes());
                    throw new RSRequestErrorException(
                            String.format("Error: No such method: %s with parameters: %s", body.getMethod(), paramNames));
                }
            }
        }
        return result;
    }
}
