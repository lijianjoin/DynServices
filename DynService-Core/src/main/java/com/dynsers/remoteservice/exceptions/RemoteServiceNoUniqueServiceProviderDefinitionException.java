package com.dynsers.remoteservice.exceptions;

public class RemoteServiceNoUniqueServiceProviderDefinitionException extends RemoteServiceException {

    private final String serviceProviderName;

    public RemoteServiceNoUniqueServiceProviderDefinitionException(String serviceProviderName) {
        super(String.format("Service Provider %s is not unique", serviceProviderName));
        this.serviceProviderName = serviceProviderName;
    }

}
