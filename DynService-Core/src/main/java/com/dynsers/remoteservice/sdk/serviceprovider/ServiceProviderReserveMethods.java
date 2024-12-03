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

package com.dynsers.remoteservice.sdk.serviceprovider;

import org.apache.commons.lang3.StringUtils;

public class ServiceProviderReserveMethods {

    private ServiceProviderReserveMethods() {
    }

    public static final String METHOD_STATUSCHECK = "000";
    public static final String METHOD_LISTSERVICES = "001";
    private static final String[] RESERVE_METHODS = {
            "toString", METHOD_STATUSCHECK, METHOD_LISTSERVICES
    };

    public static boolean isReserveMethod(String methodName) {
        for (String name : RESERVE_METHODS) {
            if (StringUtils.equals(name, methodName)) {
                return true;
            }
        }
        return false;
    }
}
