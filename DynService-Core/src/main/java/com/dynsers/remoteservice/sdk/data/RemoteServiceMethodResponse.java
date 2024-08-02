/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.data;

import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceException;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceResponseResultDeserializer;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceResponseResultSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
public class RemoteServiceMethodResponse implements Serializable {


    @JsonSerialize(using = RemoteServiceResponseResultSerializer.class)
    @JsonDeserialize(using = RemoteServiceResponseResultDeserializer.class)
    private Serializable result;

    @JsonSerialize(using = RemoteServiceResponseResultSerializer.class)
    @JsonDeserialize(using = RemoteServiceResponseResultDeserializer.class)
    private Serializable exception;

    private Class<? extends RemoteServiceException> exceptionType;

    private int status;

}
