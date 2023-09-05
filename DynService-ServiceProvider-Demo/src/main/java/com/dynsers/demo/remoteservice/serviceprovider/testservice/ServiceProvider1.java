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
package com.dynsers.demo.remoteservice.serviceprovider.testservice;

import com.dynsers.demo.dynservice.demoapi.api.TestInterface;
import com.dynsers.demo.dynservice.demoapi.api.UnknowParameterException;
import com.dynsers.core.annotations.ServiceProvider;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ServiceProvider(version = "0.0.1", uuid = "c8c7b30d-5f4c-4fde-ba57-6fb75be98ffc")
public class ServiceProvider1 implements TestInterface {
    @Override
    public String getSampleString() {
        return "Here is a sample String.";
    }

    @Override
    public String getSampleStringWithExcept() throws UnknowParameterException {
        throw new UnknowParameterException("Error was met");
    }

    public static void main(String argv[]) {
        System.out.println(String.valueOf(UUID.randomUUID()));
    }
}
