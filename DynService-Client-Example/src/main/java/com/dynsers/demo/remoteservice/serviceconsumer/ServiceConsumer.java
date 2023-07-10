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
package com.dynsers.demo.remoteservice.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceManager;

public class ServiceConsumer {

    public static void main(String argv[]) throws NoSuchFieldException, IllegalAccessException {
        RemoteServiceId id = new RemoteServiceId();
        id.setGroupId("com.dynsers.dynservice");
        id.setResourceId("testService");
        id.setResourceVersion("0.0.1");
        id.setServiceId("com.dynsers.demo.dynservice.demoapi.api.TestInterface");
        id.setServiceVersion("0.0.1");
        id.setUuid("c8c7b30d-5f4c-4fde-ba57-6fb75be98ffc");
        //id.setUuid("c8c7b30d-5f4c-4fde-ba57-6fb75be98ffd");

        DemoService d = new DemoService();
        RemoteServiceManager.getInstance().configRemoteService(d, "test", id);
        String s = d.getTest().getSampleString();
        System.out.println(s);

        d.getTest().getSampleStringWithExcept();
    }
}
