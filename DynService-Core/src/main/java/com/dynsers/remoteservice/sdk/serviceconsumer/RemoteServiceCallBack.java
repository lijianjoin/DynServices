/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.DynService.core.api.annotation.RemoteService;
import com.dynsers.DynService.core.api.enums.RemoteServiceInitialization;
import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceInjectionException;
import com.dynsers.remoteservice.utils.RemoteServiceServiceIdUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class RemoteServiceCallBack implements ReflectionUtils.FieldCallback {

    private final Object bean;

    public RemoteServiceCallBack(Object bean) {
        this.bean = bean;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(RemoteService.class)) {
            RemoteService remoteService = field.getAnnotation(RemoteService.class);
            if (RemoteServiceInitialization.SPRINGBEANINIT.equals(remoteService.initialization())) {
                ReflectionUtils.makeAccessible(field);
                RemoteServiceId id = RemoteServiceServiceIdUtils.fromAnnotation(remoteService);
                if (StringUtils.isEmpty(id.getServiceId())) {
                    id.setServiceId(field.getType().getName());
                }
                try {
                    RemoteServiceManager.getInstance().configService(bean, field.getName(), id);
                } catch (NoSuchFieldException e) {
                    throw new RemoteServiceInjectionException(e);
                }
            }
        }
    }
}
