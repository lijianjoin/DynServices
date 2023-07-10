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
