package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.data.RemoteServiceMethodRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestRequestParameterValueSerializer {

    @Test
    void testSerializeEmptyParameters() throws JsonProcessingException {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        Object[] objs = new Object[0];
        request.setParameterSerializableValues(SerializableConverterUtils.convertObjectArrayToSerializableArray(objs));
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        System.out.println(jsString);
        RemoteServiceMethodRequest rs =
                mapper.readerFor(RemoteServiceMethodRequest.class).readValue(jsString);
        assertEquals("test", rs.getMethod());
        assertEquals(0, rs.getParameterSerializableValues().length);
    }

    @Test
    void testSerializeNullParameters() throws JsonProcessingException {
        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        System.out.println(jsString);
        RemoteServiceMethodRequest rs =
                mapper.readerFor(RemoteServiceMethodRequest.class).readValue(jsString);
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
        request.setParameterSerializableValues(SerializableConverterUtils.convertObjectArrayToSerializableArray(objs));
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        System.out.println(jsString);

        RemoteServiceMethodRequest rs =
                mapper.readerFor(RemoteServiceMethodRequest.class).readValue(jsString);
        assertEquals(3, rs.getParameterSerializableValues().length);
        assertEquals(5, rs.getParameterSerializableValues()[0]);
        assertEquals("2", rs.getParameterSerializableValues()[1]);
        assertEquals(2.5, rs.getParameterSerializableValues()[2]);
    }

    @Test
    void testSerializeWithRSId() throws JsonProcessingException {

        RemoteServiceMethodRequest request = new RemoteServiceMethodRequest();
        request.setMethod("test");
        Object[] objs = new Object[3];
        objs[0] = 5;
        objs[1] = "2";
        objs[2] = Double.valueOf("2.5");
        request.setParameterSerializableValues(SerializableConverterUtils.convertObjectArrayToSerializableArray(objs));
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        System.out.println(jsString);

        RemoteServiceMethodRequest rs =
                mapper.readerFor(RemoteServiceMethodRequest.class).readValue(jsString);
        assertEquals(3, rs.getParameterSerializableValues().length);
        assertEquals(5, rs.getParameterSerializableValues()[0]);
        assertEquals("2", rs.getParameterSerializableValues()[1]);
        assertEquals(2.5, rs.getParameterSerializableValues()[2]);
    }
}
