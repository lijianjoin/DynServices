/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.dynsers.remoteservice.server.data.mapper;

import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import com.dynsers.core.data.RemoteServiceId;

public class RemoteServiceProviderMapper {

    //RemoteServiceProviderMapper MAPPER = Mappers.getMapper( RemoteServiceProviderMapper.class );

    public static RemoteServiceProviderEntity toEntity(RemoteServiceId source) {
        if(source == null) {
            return null;
        }
        RemoteServiceProviderEntity res = new RemoteServiceProviderEntity();
        res.setUri(source.getUri());
        res.setServiceId(source.getServiceId());
        res.setServiceVersion(source.getServiceVersion());
        res.setGroupId(source.getGroupId());
        res.setDetectionInterval(source.getDetectInterval());
        res.setResourceId(source.getResourceId());
        res.setResourceVersion(source.getResourceVersion());
        res.setUuid(source.getUuid());
        res.setAdditionalInfo(source.getAdditionalInfo());
        return res;
    }

    public static RemoteServiceId toDTO(RemoteServiceProviderEntity source) {
        if(source == null) {
            return null;
        }
        RemoteServiceId res = new RemoteServiceId();
        res.setUri(source.getUri());
        res.setGroupId(source.getGroupId());
        res.setResourceId(source.getResourceId());
        res.setResourceVersion(source.getResourceVersion());
        res.setServiceId(source.getServiceId());
        res.setServiceVersion(source.getServiceVersion());
        res.setDetectInterval(source.getDetectionInterval());
        res.setUuid(source.getUuid());
        res.setAdditionalInfo(source.getAdditionalInfo());
        return res;
    }
}
