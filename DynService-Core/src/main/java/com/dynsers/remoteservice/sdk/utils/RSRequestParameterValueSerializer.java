/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class RSRequestParameterValueSerializer extends StdSerializer<Object[]> {

    public RSRequestParameterValueSerializer() {
        this(null);
    }
    protected RSRequestParameterValueSerializer(Class<Object[]> t) {
        super(t);
    }

    @Override
    public void serialize(Object[] objects, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String res = "";
        if(objects != null && objects.length != 0){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(objects);
            oos.close();
            res = Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        jsonGenerator.writeString(res);
    }

}
