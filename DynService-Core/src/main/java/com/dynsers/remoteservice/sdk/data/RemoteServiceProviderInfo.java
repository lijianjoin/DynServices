/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RemoteServiceProviderInfo implements Serializable {


    private final Map<String, Map<String, Map<String, Map<String, List<String>>>>> providerInfoContainer
            = new HashMap<>();
    private String className;


    public void addRemoteServiceId(RemoteServiceId id) throws ClassNotFoundException {
        Map<String, Map<String, Map<String, List<String>>>> group = providerInfoContainer.computeIfAbsent(id.getGroupId(), k -> new HashMap<>());
        String resourceKey = id.getResourceId() + "_" + id.getResourceVersion();
        Map<String, Map<String, List<String>>> resource = group.computeIfAbsent(resourceKey, k -> new HashMap<>());
        String serviceKey = id.getServiceId() + "_" + id.getServiceVersion();
        Map<String, List<String>> provider = resource.computeIfAbsent(serviceKey, k -> new HashMap<>());
        Class<?> clz = Class.forName(id.getServiceId());
        provider.put(id.getUuid(), Arrays.stream(clz.getMethods()).map(Method::getName).collect(Collectors.toList()));
    }

}
