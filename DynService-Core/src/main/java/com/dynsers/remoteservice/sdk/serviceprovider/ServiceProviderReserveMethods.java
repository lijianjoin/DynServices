package com.dynsers.remoteservice.sdk.serviceprovider;

import org.apache.commons.lang3.StringUtils;

public class ServiceProviderReserveMethods {

    public final static String METHOD_STATUSCHECK = "000";
    public final static String METHOD_LISTSERVICES = "001";
    private static String[] RESERVE_METHODS = {
            "toString", METHOD_STATUSCHECK, METHOD_LISTSERVICES
    };

    public static boolean isReserveMethod(String methodName) {
        for(String name : RESERVE_METHODS) {
            if(StringUtils.equals(name, methodName)) {
                return true;
            }
        }
        return false;
    }
}
