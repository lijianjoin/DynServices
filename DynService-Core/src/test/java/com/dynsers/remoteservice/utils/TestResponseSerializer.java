package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.exceptions.RemoteServiceUriOccupiedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestResponseSerializer {

    @Test
    void testSerializeResponseWithException() throws JsonProcessingException {
        RemoteServiceMethodResponse resp = new RemoteServiceMethodResponse();
        resp.setResult(20);
        resp.setException(new RemoteServiceUriOccupiedException(""));
        resp.setExceptionType(RemoteServiceUriOccupiedException.class);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp);
        System.out.println(jsString);
        RemoteServiceMethodResponse rs =
                mapper.readerFor(RemoteServiceMethodResponse.class).readValue(jsString);
        assertEquals(20, rs.getResult());
        Class<?> exceptionType = resp.getExceptionType();

        Exception except = (Exception) exceptionType.cast(resp.getException());
        assertEquals(RemoteServiceUriOccupiedException.class, except.getClass());
    }

    @Test
    void testSerializeNormalResponse() throws JsonProcessingException {
        RemoteServiceMethodResponse resp = new RemoteServiceMethodResponse();
        resp.setResult(20);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp);
        System.out.println(jsString);
        RemoteServiceMethodResponse rs =
                mapper.readerFor(RemoteServiceMethodResponse.class).readValue(jsString);
        assertEquals(20, rs.getResult());
    }
}
