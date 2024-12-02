/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class RemoteServiceProviderInfoUtils {

    private RemoteServiceProviderInfoUtils() {
    }

    public static String getFormattedRemoteServiceId(RemoteServiceId id) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(id);
    }
}
