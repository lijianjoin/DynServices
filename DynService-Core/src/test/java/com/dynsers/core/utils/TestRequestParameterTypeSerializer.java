package com.dynsers.core.utils;

import com.dynsers.core.data.RSMethodRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRequestParameterTypeSerializer {


    @Test
    public void testSerializeTypeNotFound() throws JsonProcessingException {


        RSMethodRequest request = new RSMethodRequest();
        request.setMethod("test");
        Class<?>[]  clss = new Class<?>[1];
        clss[0] = Integer.class;
        request.setParameterTypes(clss);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);

        jsString = jsString.replaceAll("java.lang.Integer", "java.lang.InTTT");
        System.out.println(jsString);

        try {
            mapper
                    .readerFor(RSMethodRequest.class)
                    .readValue(jsString);
        }
        catch (JsonProcessingException e) {
            assertEquals(JsonMappingException.class, e.getClass());
        }

    }

    @Test
    public void testSerializeEmptyTypes() throws JsonProcessingException {

        RSMethodRequest request = new RSMethodRequest();
        request.setMethod("test");
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);

        RSMethodRequest rs = mapper
                .readerFor(RSMethodRequest.class)
                .readValue(jsString);
        assertEquals(null, rs.getParameterTypes());
    }


    @Test
    public void testSerializeJson() throws JsonProcessingException {

        RSMethodRequest request = new RSMethodRequest();
        request.setMethod("test");
        Class<?>[]  clss = new Class<?>[3];
        clss[0] = Integer.class;
        clss[1] = String.class;
        clss[2] = Double.class;
        request.setParameterTypes(clss);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);

        RSMethodRequest rs = mapper
                .readerFor(RSMethodRequest.class)
                .readValue(jsString);
        assertEquals(3, rs.getParameterTypes().length);
        assertEquals(Integer.class, rs.getParameterTypes()[0]);
        assertEquals(String.class, rs.getParameterTypes()[1]);
        assertEquals(Double.class, rs.getParameterTypes()[2]);
    }

}
