package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Arrays;

public class RSRequestParameterTypeSerializer extends StdSerializer<Class<?>[]> {

    public RSRequestParameterTypeSerializer(){
        this(null);
    }

    protected RSRequestParameterTypeSerializer(Class<Class<?>[]> t) {
        super(t);
    }

    @Override
    public void serialize(Class<?>[] classes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String res = "";
        StringBuilder sb = new StringBuilder();
        if(classes != null && classes.length != 0){
            Arrays.stream(classes).forEach(cls -> {
                if( cls != null ) {
                    sb.append(cls.getName());
                    sb.append(";");
                }
            });
        }
        res = sb.toString();
        jsonGenerator.writeString(res);
    }
}
