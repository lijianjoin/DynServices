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
import java.io.Serializable;
import java.util.Base64;

public class RemoteServiceResponseResultSerializer extends StdSerializer<Serializable> {

    /**
     * This default constructor will be used by Jackson, please do not delete
     */
    public RemoteServiceResponseResultSerializer() {
        this(null);
    }

    protected RemoteServiceResponseResultSerializer(Class<Serializable> t) {
        super(t);
    }

    @Override
    public void serialize(Serializable obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        var res = "";
        if (obj != null) {
            var baos = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            res = Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        jsonGenerator.writeString(res);
    }

}
