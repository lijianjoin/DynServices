package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceRequestParameterSerializableException;
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
