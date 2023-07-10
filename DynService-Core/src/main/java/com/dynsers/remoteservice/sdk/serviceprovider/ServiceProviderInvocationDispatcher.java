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
package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.sdk.data.RSMethodRequest;
import com.dynsers.remoteservice.sdk.data.RSMethodResponse;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSException;
import com.dynsers.remoteservice.sdk.exceptions.RSInvocationException;
import com.dynsers.remoteservice.sdk.utils.RSExceptionUtils;
import com.dynsers.remoteservice.sdk.utils.RSRequestHelper;
import com.dynsers.remoteservice.sdk.utils.RSResposeHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ServiceProviderStartupListener serviceConfig;

    public ServiceProviderInvocationDispatcher() {

    }

    @RequestMapping(value = "**")
    public ResponseEntity<RSMethodResponse> invokeServiceProvider(@RequestBody RSMethodRequest body, HttpServletRequest request) throws ClassNotFoundException {
        System.out.println(request.getRequestURI());
        Class<?> serviceProviderClass = Class.forName(body.getServiceId());
        ResponseEntity<RSMethodResponse> res = RSRequestHelper.checkRequest(body, serviceProviderClass);
        Method m = null;
        try {
            m = serviceProviderClass.getMethod(body.getMethod(), body.getParameterTypes());
        }
        catch (NoSuchMethodException noMethodExcept) {
            if(StringUtils.equals("toString", body.getMethod())) {
                RSMethodResponse responseBody = new RSMethodResponse();
                responseBody.setResult(serviceProviderClass.toString());
                res = new ResponseEntity<>(responseBody, HttpStatus.OK);
                return res;
            }
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

}
