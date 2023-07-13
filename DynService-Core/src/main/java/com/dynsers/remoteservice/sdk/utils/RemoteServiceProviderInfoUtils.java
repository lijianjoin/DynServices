package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class RemoteServiceProviderInfoUtils {


    public static String getFormattedRemoteServiceId(RemoteServiceId id) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(id);
        return json;
    }
}
