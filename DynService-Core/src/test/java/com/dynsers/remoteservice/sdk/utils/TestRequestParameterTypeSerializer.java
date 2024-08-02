package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dynsers.remoteservice.sdk.data.RemoteServiceMethodRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestRequestParameterTypeSerializer {


    @Test
    void testSerializeTypeNotFound() throws JsonProcessingException {


        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        Class<?>[] clss = new Class<?>[1];
        clss[0] = Integer.class;
        request.setParameterTypes(clss);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);

        jsString = jsString.replaceAll("java.lang.Integer", "java.lang.InTTT");
        System.out.println(jsString);

        try {
            mapper
                    .readerFor(RemoteServiceMethodRequest.class)
                    .readValue(jsString);
        } catch (JsonProcessingException e) {
            assertEquals(JsonMappingException.class, e.getClass());
        }

    }

    @Test
    void testSerializeEmptyTypes() throws JsonProcessingException {

        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);

        RemoteServiceMethodRequest rs = mapper
                .readerFor(RemoteServiceMethodRequest.class)
                .readValue(jsString);
        assertNull(rs.getParameterTypes());
    }


    @Test
    void testSerializeJson() throws JsonProcessingException {

        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        Class<?>[] clss = {Integer.class, String.class, double.class, int.class, long.class, float.class,
                boolean.class, char.class, byte.class, short.class, void.class};
        request.setParameterTypes(clss);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);

        RemoteServiceMethodRequest rs = mapper
                .readerFor(RemoteServiceMethodRequest.class)
                .readValue(jsString);
        assertEquals(clss.length, rs.getParameterTypes().length);
        for (int i = 0; i < clss.length; i++) {
            assertEquals(clss[i], rs.getParameterTypes()[i]);
        }
    }
}
