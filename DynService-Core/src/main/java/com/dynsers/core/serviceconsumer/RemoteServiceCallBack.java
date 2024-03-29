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
package com.dynsers.core.serviceconsumer;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class RemoteServiceCallBack implements ReflectionUtils.FieldCallback {

    Object bean;
    public RemoteServiceCallBack(Object bean) {
        this.bean = bean;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        RemoteServiceProxy.setField(bean, field);
    }
}
