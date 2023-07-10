package com.dynsers.remoteservice.sdk.data;

import com.dynsers.remoteservice.sdk.utils.RSRequestParameterTypeDeserializer;
import com.dynsers.remoteservice.sdk.utils.RSRequestParameterTypeSerializer;
import com.dynsers.remoteservice.sdk.utils.RSRequestParameterValueDeserializer;
import com.dynsers.remoteservice.sdk.utils.RSRequestParameterValueSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
public class RSMethodRequest implements Serializable {

    private String serviceId;

    private String method;

    private String serviceVersion;

    @JsonSerialize(using = RSRequestParameterTypeSerializer.class)
    @JsonDeserialize(using = RSRequestParameterTypeDeserializer.class)
    private Class[] parameterTypes;

    @JsonSerialize(using = RSRequestParameterValueSerializer.class)
    @JsonDeserialize(using = RSRequestParameterValueDeserializer.class)
    private Object[] parameterValues;

}
