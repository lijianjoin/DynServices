/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceClassNotFoundInDeserialize;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Base64;

public class RemoteServiceRequestParameterValueDeserializer extends StdDeserializer<Object[]> {


    /**
     * This constructor will be used in the Jackson Writer.
     * Please do not delete it
     */
    public RemoteServiceRequestParameterValueDeserializer() {
        this(null);
    }

    protected RemoteServiceRequestParameterValueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Object[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String content = jsonParser.getText();
        if (StringUtils.isEmpty(content)) {
            return new Serializable[0];
        }
        byte[] data = Base64.getDecoder().decode(content);
        var ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object[] res;
        try {
            res = (Object[]) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RemoteServiceClassNotFoundInDeserialize(e);
        }
        ois.close();
        return SerializableConverterUtils.convertObjectArrayToSerializableArray(res);
    }
}
