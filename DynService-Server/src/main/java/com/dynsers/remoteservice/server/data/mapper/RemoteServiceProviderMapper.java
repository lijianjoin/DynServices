/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.server.data.mapper;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RemoteServiceProviderMapper {


    RemoteServiceProviderEntity toEntity(RemoteServiceId remoteServiceId);

    RemoteServiceId toDto(RemoteServiceProviderEntity remoteServiceProviderEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RemoteServiceProviderEntity partialUpdate(
            RemoteServiceId remoteServiceId, @MappingTarget RemoteServiceProviderEntity remoteServiceProviderEntity);
}
