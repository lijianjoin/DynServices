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
package com.dynsers.core.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class RemoteServiceProviderInfo implements Serializable {


    private String className;

    private final Map<String, Map<String, Map<String, Map<String, List<String>>>>> providerInfoContainer
            = new HashMap<>();


    public RemoteServiceProviderInfo() {
    }

    public void addRemoteServiceId(RemoteServiceId id) throws ClassNotFoundException {
        Map<String, Map<String, Map<String, List<String>>>> group = providerInfoContainer.computeIfAbsent(id.getGroupId(), k -> new HashMap<>());
        String resourceKey = id.getResourceId() + "_" + id.getResourceVersion();
        Map<String, Map<String, List<String>>> resource = group.computeIfAbsent(resourceKey, k -> new HashMap<>());
        String serviceKey = id.getServiceId() + "_" + id.getServiceVersion();
        Map<String, List<String>> provider = resource.computeIfAbsent(serviceKey, k -> new HashMap<>());
        Class<?> clz = Class.forName(id.getServiceId());
        provider.put(id.getUuid(), Arrays.stream(clz.getMethods()).map(Method::getName).collect(Collectors.toList()));
    }

    public static void main(String argv[]) throws JsonProcessingException {
        RemoteServiceProviderInfo test = new RemoteServiceProviderInfo();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(test);
        System.out.println(json);
    }

}
