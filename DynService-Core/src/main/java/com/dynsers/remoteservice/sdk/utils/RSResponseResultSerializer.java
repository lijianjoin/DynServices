package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class RSResponseResultSerializer extends StdSerializer<Serializable> {

    public RSResponseResultSerializer() {
        this(null);
    }

    protected RSResponseResultSerializer(Class<Serializable> t) {
        super(t);
    }

    @Override
    public void serialize(Serializable obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String res = "";
        if(obj != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            res = Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        jsonGenerator.writeString(res);
    }

}
