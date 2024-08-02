/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Arrays;

public class RemoteServiceRequestParameterTypeSerializer extends StdSerializer<Class<?>[]> {

    /**
     * This constructor will be used in the Jackson Writer.
     * Please do not delete it
     */
    public RemoteServiceRequestParameterTypeSerializer() {
        this(null);
    }

    protected RemoteServiceRequestParameterTypeSerializer(Class<Class<?>[]> t) {
        super(t);
    }

    @Override
    public void serialize(Class<?>[] classes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String res;
        var sb = new StringBuilder();
        if (classes != null && classes.length != 0) {
            Arrays.stream(classes).forEach(cls -> {
                if (cls != null) {
                    sb.append(cls.getName());
                    sb.append(";");
                }
            });
        }
        res = sb.toString();
        jsonGenerator.writeString(res);
    }
}
