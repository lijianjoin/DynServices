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

package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.exceptions.RemoteServiceRequestParameterSerializableException;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SerializableConverterUtils {

    private SerializableConverterUtils() {
    }


    public static Serializable[] convertObjectArrayToSerializableArray(Object[] objs)
            throws RemoteServiceRequestParameterSerializableException {

        List<Serializable> tempRes = new LinkedList<>();
        if (null != objs) {
            for (Object obj : objs) {
                if (obj instanceof Serializable || ClassUtils.isPrimitiveOrWrapper(obj.getClass())) {
                    tempRes.add((Serializable) obj);
                } else {
                    var msg = String.format("Class: %s in the remote service request can not be serializable. " +
                            "Please check the class definition.", obj.getClass().getName());
                    throw new RemoteServiceRequestParameterSerializableException(msg);
                }
            }
        }
        return tempRes.toArray(Serializable[]::new);
    }

    public static Serializable convertObjectToSerializable(Object obj)
            throws RemoteServiceRequestParameterSerializableException {

        if (null == obj) {
            return null;
        }
        if (obj instanceof Serializable || ClassUtils.isPrimitiveOrWrapper(obj.getClass())) {
            return (Serializable) obj;
        } else {
            var msg = String.format("Class: %s in the remote service request can not be serializable. " +
                    "Please check the class definition.", obj.getClass().getName());
            throw new RemoteServiceRequestParameterSerializableException(msg);
        }
    }
}
