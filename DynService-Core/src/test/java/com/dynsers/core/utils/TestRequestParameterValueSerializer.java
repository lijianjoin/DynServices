package com.dynsers.core.utils;

import com.dynsers.core.data.RSMethodRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRequestParameterValueSerializer {



    @Test
    public void testSerializeEmptyParameters() throws JsonProcessingException {
        RSMethodRequest request = new RSMethodRequest();
        request.setMethod("test");
        Object[]  objs = new Object[0];
        request.setParameterValues(objs);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);
        RSMethodRequest rs = mapper
                .readerFor(RSMethodRequest.class)
                .readValue(jsString);
        assertEquals("test", rs.getMethod());
        assertEquals(0, rs.getParameterValues().length);
    }

    @Test
    public void testSerializeNullParameters() throws JsonProcessingException {
        RSMethodRequest request = new RSMethodRequest();
        request.setMethod("test");
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);
        RSMethodRequest rs = mapper
                .readerFor(RSMethodRequest.class)
                .readValue(jsString);
        assertEquals("test", rs.getMethod());
    }

    @Test
    public void testSerializeParamsJson() throws JsonProcessingException {

        RSMethodRequest request = new RSMethodRequest();
        request.setMethod("test");
        Object[]  objs = new Object[3];
        objs[0] = Integer.valueOf(5);
        objs[1] = String.valueOf("2");
        objs[2] = Double.valueOf("2.5");
        request.setParameterValues(objs);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);

        RSMethodRequest rs = mapper
                .readerFor(RSMethodRequest.class)
                .readValue(jsString);
        assertEquals(3, rs.getParameterValues().length);
        assertEquals(5, rs.getParameterValues()[0]);
        assertEquals("2", rs.getParameterValues()[1]);
        assertEquals(2.5, rs.getParameterValues()[2]);
    }

    @Test
    public void testSerializeWithRSId() throws JsonProcessingException {

        RSMethodRequest request = new RSMethodRequest();
        request.setMethod("test");
        Object[]  objs = new Object[3];
        objs[0] = Integer.valueOf(5);
        objs[1] = String.valueOf("2");
        objs[2] = Double.valueOf("2.5");
        request.setParameterValues(objs);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(jsString);

        RSMethodRequest rs = mapper
                .readerFor(RSMethodRequest.class)
                .readValue(jsString);
        assertEquals(3, rs.getParameterValues().length);
        assertEquals(5, rs.getParameterValues()[0]);
        assertEquals("2", rs.getParameterValues()[1]);
        assertEquals(2.5, rs.getParameterValues()[2]);
    }

}
