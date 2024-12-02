/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.utils;

import java.util.Arrays;

public class RemoteServiceRequestHelper {

    private RemoteServiceRequestHelper() {
    }

    public static String getParamterTypesAsString(Class<?>... parameterTypes) {
        var sb = new StringBuilder();

        if (parameterTypes != null) {
            Arrays.stream(parameterTypes).forEach(val -> {
                sb.append(val);
                sb.append(";");
            });
        }

        return sb.toString();
    }

}
