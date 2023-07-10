package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dynsers.remoteservice.sdk.data.RSMethodResponse;
import com.dynsers.remoteservice.sdk.exceptions.RSSURIOccupiedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestResponseSerializer {


    @Test
    public void testSerializeResponseWithExcpetion() throws JsonProcessingException {
        RSMethodResponse resp = new RSMethodResponse();
        resp.setResult(20);
        resp.setException(new RSSURIOccupiedException(""));
        resp.setExceptionType(RSSURIOccupiedException.class);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(resp);
        System.out.println(jsString);
        RSMethodResponse rs = mapper
                .readerFor(RSMethodResponse.class)
                .readValue(jsString);
        assertEquals(20, rs.getResult());
        Class<?> exceptionType = resp.getExceptionType();

        Exception except = (Exception) exceptionType.cast(resp.getException());
        assertEquals(RSSURIOccupiedException.class, except.getClass());
    }


    @Test
    public void testSerializeNormalResponse() throws JsonProcessingException {
        RSMethodResponse resp = new RSMethodResponse();
        resp.setResult(20);
        ObjectMapper mapper = new ObjectMapper();
        String jsString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(resp);
        System.out.println(jsString);
        RSMethodResponse rs = mapper
                .readerFor(RSMethodResponse.class)
                .readValue(jsString);
        assertEquals(20, rs.getResult());
    }
}
