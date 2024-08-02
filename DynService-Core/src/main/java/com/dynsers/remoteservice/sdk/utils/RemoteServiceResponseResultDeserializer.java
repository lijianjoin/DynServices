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

public class RemoteServiceResponseResultDeserializer extends StdDeserializer<Serializable> {

    public RemoteServiceResponseResultDeserializer() {
        this(null);
    }

    protected RemoteServiceResponseResultDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Serializable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        var content = jsonParser.getText();
        if (StringUtils.isEmpty(content)) {
            return new Object[0];
        }
        byte[] data = Base64.getDecoder().decode(content);
        var ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Serializable result;
        try {
            result = (Serializable) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RemoteServiceClassNotFoundInDeserialize(e);
        }
        ois.close();
        return result;
    }
}
