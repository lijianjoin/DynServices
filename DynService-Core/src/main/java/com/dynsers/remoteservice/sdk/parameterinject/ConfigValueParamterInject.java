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
package com.dynsers.remoteservice.sdk.parameterinject;

import com.dynsers.remoteservice.sdk.annotations.ConfigValue;
import com.dynsers.remoteservice.sdk.configuration.RSPropertyResolver;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ConfigValueParamterInject {

    public static void injectConfigValue(Object obj) throws IllegalAccessException {
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields) {
            if (field.isAnnotationPresent(ConfigValue.class)) {
                ReflectionUtils.makeAccessible(field);
                ConfigValue config = field.getAnnotation(ConfigValue.class);
                String key = config.value();
                String value = RSPropertyResolver.getPropertyValue(key);
                field.set(obj, value);
            }
        }
    }

}
