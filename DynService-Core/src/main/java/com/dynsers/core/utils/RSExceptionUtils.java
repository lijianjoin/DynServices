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
package com.dynsers.core.utils;

import com.dynsers.core.exceptions.RSException;
import com.dynsers.core.exceptions.RSInvocationException;

import java.lang.reflect.InvocationTargetException;

public class RSExceptionUtils {

    public static RSException getConcreteRSException(InvocationTargetException invocException) {
        if(RSException.class.isAssignableFrom(invocException.getTargetException().getClass())) {
            return (RSException) invocException.getTargetException().getClass().cast(invocException.getTargetException());
        }
        return new RSInvocationException(invocException.getTargetException());
    }
}
