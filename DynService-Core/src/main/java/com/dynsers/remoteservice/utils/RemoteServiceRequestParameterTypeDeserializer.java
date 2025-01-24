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

import com.dynsers.remoteservice.exceptions.RemoteServiceInvocationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class RemoteServiceRequestParameterTypeDeserializer extends StdDeserializer<Class<?>[]> {

    @SuppressWarnings("unused") // needed for Jackson
    public RemoteServiceRequestParameterTypeDeserializer() {
        this(null);
    }

    protected RemoteServiceRequestParameterTypeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Class<?>[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Class<?>[] res = new Class<?>[0];
        String names = jsonParser.getText();
        List<Class<?>> resList = new ArrayList<>();
        if (StringUtils.isNotEmpty(names)) {
            String[] clsNames = names.split(";");
            Arrays.stream(clsNames).forEach(val -> {
                if (StringUtils.isNotEmpty(val)) {
                    try {
                        resList.add(parseType(val));
                    } catch (IllegalArgumentException e) {
                        log.error("RemoteServiceRequestParameterTypeDeserializer.deserialize > could not parse the type of {}, because {}", val, e.getMessage());
                        throw new RemoteServiceInvocationException(e);
                    }
                }
            });
            res = new Class[resList.size()];
            int pos = 0;
            for (Class<?> cls : resList) {
                res[pos] = cls;
                pos++;
            }
        }
        return res;
    }

    /**
     * Return the java {@link Class} object with the specified class name.
     * This is an "extended" {@link Class#forName(String) } operation.
     * + It is able to return Class objects for primitive types
     * + Classes in name space `java.lang` do not need the fully qualified name
     * + The class name can be fully qualified or not.
     *
     * @param className The class name, never `null`
     * @throws IllegalArgumentException if no class can be loaded
     */
    private static Class<?> parseType(@NonNull final String className) throws IllegalArgumentException {
        return switch (className) {
            case "boolean" -> boolean.class;
            case "byte" -> byte.class;
            case "short" -> short.class;
            case "int" -> int.class;
            case "long" -> long.class;
            case "float" -> float.class;
            case "double" -> double.class;
            case "char" -> char.class;
            case "void" -> void.class;
            default -> {
                final String fullQualifiedClassName = className.contains(".") ? className : "java.lang.".concat(className);
                try {
                    yield Class.forName(fullQualifiedClassName);
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }
}
