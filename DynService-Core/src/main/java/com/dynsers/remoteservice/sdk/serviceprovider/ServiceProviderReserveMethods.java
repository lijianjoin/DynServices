package com.dynsers.remoteservice.sdk.serviceprovider;

import org.apache.commons.lang3.StringUtils;

public class ServiceProviderReserveMethods {

    public final static String METHOD_STATUSCHECK = "000";
    private static String[] RESERVE_METHODS = {
            "toString", METHOD_STATUSCHECK
    };


    public static boolean isStatusCheckMethod(String methodName) {
        return StringUtils.equals(METHOD_STATUSCHECK, methodName);
    }

    public static boolean isReserveMethod(String methodName) {
        for(String name : RESERVE_METHODS) {
            if(StringUtils.equals(name, methodName)) {
                return true;
            }
        }
        return false;
    }
}
