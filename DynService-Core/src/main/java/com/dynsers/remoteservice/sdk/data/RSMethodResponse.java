package com.dynsers.remoteservice.sdk.data;

import com.dynsers.remoteservice.sdk.exceptions.RSException;
import com.dynsers.remoteservice.sdk.utils.RSResponseResultDeserializer;
import com.dynsers.remoteservice.sdk.utils.RSResponseResultSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
public class RSMethodResponse  implements Serializable {


    @JsonSerialize(using = RSResponseResultSerializer.class)
    @JsonDeserialize(using = RSResponseResultDeserializer.class)
    private Object result;

    @JsonSerialize(using = RSResponseResultSerializer.class)
    @JsonDeserialize(using = RSResponseResultDeserializer.class)
    private Object exception;

    private Class<? extends RSException> exceptionType;

    private int status;

}
