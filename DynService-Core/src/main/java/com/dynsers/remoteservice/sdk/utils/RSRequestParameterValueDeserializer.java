package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class RSRequestParameterValueDeserializer extends StdDeserializer<Object[]> {


    public RSRequestParameterValueDeserializer(){
        this(null);
    }

    protected RSRequestParameterValueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Object[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String content = jsonParser.getText();
        if(StringUtils.isEmpty(content)) {
            return new Object[0];
        }
        byte [] data = Base64.getDecoder().decode( content );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object[] res  = new Object[0];
        try {
            res = (Object[]) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ois.close();
        return res;
    }
}
