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

package com.dynsers.remoteservice.server.services;

import com.dynsers.core.container.ServiceIdContainer;

public class RegisterContainer {

    private static ServiceIdContainer serviceIdContainer;

    public static ServiceIdContainer getServiceIdContainer() {
        ServiceIdContainer localRef = serviceIdContainer;
        if (localRef == null) {
            synchronized (RegisterContainer.class) {
                localRef = serviceIdContainer;
                if (localRef == null) {
                    serviceIdContainer = localRef = new ServiceIdContainer();
                }
            }
        }
        return localRef;
    }

    // write a function to sum two intergers
    // write a test for this functio
    public static void main(String[] args) {

    }



}
