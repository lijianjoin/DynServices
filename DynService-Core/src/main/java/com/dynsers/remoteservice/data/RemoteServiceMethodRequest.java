
/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dynsers.remoteservice.data;

import com.dynsers.remoteservice.enums.RequestSource;
import com.dynsers.remoteservice.utils.RemoteServiceRequestParameterTypeDeserializer;
import com.dynsers.remoteservice.utils.RemoteServiceRequestParameterTypeSerializer;
import com.dynsers.remoteservice.utils.RemoteServiceRequestParameterValueDeserializer;
import com.dynsers.remoteservice.utils.RemoteServiceRequestParameterValueSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.annotation.Nullable;
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

    @Nullable
    private String uuid;

    // Default value is REST_WEB, which means the request is from RESTful web service
    @Nullable
    private RequestSource requestSource = RequestSource.REST_WEB;

    @JsonSerialize(using = RemoteServiceRequestParameterTypeSerializer.class)
    @JsonDeserialize(using = RemoteServiceRequestParameterTypeDeserializer.class)
    @Nullable
    private Class<?>[] parameterTypes;

    @JsonSerialize(using = RemoteServiceRequestParameterValueSerializer.class)
    @JsonDeserialize(using = RemoteServiceRequestParameterValueDeserializer.class)
    @Nullable
    private Serializable[] parameterSerializableValues;

    @Nullable
    private Object[] parameterRawValues;
}
