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
