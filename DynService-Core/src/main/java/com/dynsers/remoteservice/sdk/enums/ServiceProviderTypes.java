/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.enums;

public enum ServiceProviderTypes {

    REMOTESERVICEPROVIDER("PROVIDER"),

    REMOTESERVICEPREGISTER("REGISTER");

    private String value;

    ServiceProviderTypes(String value) {
        this.value = value;
    }

    public String getString() {
        return value;
    }
}
