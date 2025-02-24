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
package com.dynsers.demo.dynservice.demoapi.api;

import com.dynsers.demo.dynservice.demoapi.data.ComplexParameter;

import com.dynsers.DynService.core.api.annotation.RemoteService;
import com.dynsers.DynService.core.api.enums.RemoteServiceInitialization;

@RemoteService(
        groupId = "com.dynsers.demo",
        resourceId = "dynservice",
        resourceVersion = "1.0.0",
        serviceId = "test",
        serviceVersion = "1.0.0",
        serviceName = "test",
        serviceLocation = "http://localhost:8080",
        uuid = "test-uuid",
        url = "/test",
        environment = "dev",
        initialization = RemoteServiceInitialization.SPRINGBEANINIT)
public interface TestInterface {

    String getSampleString();

    String testIncomingMethod(String input);

    String testMethodWithComplexParameter(ComplexParameter complexParameter);

    String getSampleStringWithExcept() throws UnknowParameterException;

    ComplexParameter getPersonInfo(int id);
}
