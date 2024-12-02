/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class RemoteServiceRequestParameterValueSerializer extends StdSerializer<Object[]> {

    public RemoteServiceRequestParameterValueSerializer() {
        this(null);
    }

    protected RemoteServiceRequestParameterValueSerializer(Class<Object[]> t) {
        super(t);
    }

    @Override
    public void serialize(Object[] objects, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        var res = "";
        if (objects != null && objects.length != 0) {
            var baos = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(baos);
            oos.writeObject(objects);
            oos.close();
            res = Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        jsonGenerator.writeString(res);
    }

}
