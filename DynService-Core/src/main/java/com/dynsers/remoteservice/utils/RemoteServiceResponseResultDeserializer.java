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

import com.dynsers.remoteservice.exceptions.RemoteServiceClassNotFoundInDeserialize;
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
