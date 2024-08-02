package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dynsers.remoteservice.sdk.data.RemoteServiceMethodRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestRequestParameterValueSerializer {


    @Test
    void testSerializeEmptyParameters() throws JsonProcessingException {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        Object[] objs = new Object[0];
        request.setParameterValues(SerializableConverterUtils.convertObjectArrayToSerializableArray(objs));
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);
        RemoteServiceMethodRequest rs = mapper
                .readerFor(RemoteServiceMethodRequest.class)
                .readValue(jsString);
        assertEquals("test", rs.getMethod());
        assertEquals(0, rs.getParameterValues().length);
    }

    @Test
    void testSerializeNullParameters() throws JsonProcessingException {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);
        RemoteServiceMethodRequest rs = mapper
                .readerFor(RemoteServiceMethodRequest.class)
                .readValue(jsString);
        assertEquals("test", rs.getMethod());
    }

    @Test
    void testSerializeParamsJson() throws JsonProcessingException {

        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        Object[] objs = new Object[3];
        objs[0] = 5;
        objs[1] = "2";
        objs[2] = Double.valueOf("2.5");
        request.setParameterValues(SerializableConverterUtils.convertObjectArrayToSerializableArray(objs));
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);

        RemoteServiceMethodRequest rs = mapper
                .readerFor(RemoteServiceMethodRequest.class)
                .readValue(jsString);
        assertEquals(3, rs.getParameterValues().length);
        assertEquals(5, rs.getParameterValues()[0]);
        assertEquals("2", rs.getParameterValues()[1]);
        assertEquals(2.5, rs.getParameterValues()[2]);
    }

    @Test
    void testSerializeWithRSId() throws JsonProcessingException {

        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        Object[] objs = new Object[3];
        objs[0] = 5;
        objs[1] = "2";
        objs[2] = Double.valueOf("2.5");
        request.setParameterValues(SerializableConverterUtils.convertObjectArrayToSerializableArray(objs));
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);

        RemoteServiceMethodRequest rs = mapper
                .readerFor(RemoteServiceMethodRequest.class)
                .readValue(jsString);
        assertEquals(3, rs.getParameterValues().length);
        assertEquals(5, rs.getParameterValues()[0]);
        assertEquals("2", rs.getParameterValues()[1]);
        assertEquals(2.5, rs.getParameterValues()[2]);
    }

}
