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
