/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.data;

import com.dynsers.remoteservice.sdk.utils.RemoteServiceRequestParameterTypeDeserializer;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceRequestParameterTypeSerializer;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceRequestParameterValueDeserializer;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceRequestParameterValueSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteServiceMethodRequest implements Serializable {

    private String serviceId;

    private String method;

    private String serviceVersion;

    private String serviceName;

    private String uuid;

    @JsonSerialize(using = RemoteServiceRequestParameterTypeSerializer.class)
    @JsonDeserialize(using = RemoteServiceRequestParameterTypeDeserializer.class)
    private Class<?>[] parameterTypes;

    @JsonSerialize(using = RemoteServiceRequestParameterValueSerializer.class)
    @JsonDeserialize(using = RemoteServiceRequestParameterValueDeserializer.class)
    private Serializable[] parameterValues;

}
