package com.dynsers.remoteservice.sdk.enums;

public enum ServerProviderTypes {

    REMOTESERVICEPROVIDER("PROVIDER"),

    REMOTESERVICEPREGISTER("REGISTER");

    private String value;
    private ServerProviderTypes(String value) {
        value = value;
    }

    public String getString() {
        return value;
    }
}
