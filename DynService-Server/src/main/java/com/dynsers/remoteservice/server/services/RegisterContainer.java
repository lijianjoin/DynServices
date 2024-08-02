/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.server.services;

import com.dynsers.remoteservice.sdk.container.ServiceIdContainer;

public class RegisterContainer {

    private static ServiceIdContainer serviceIdContainer;

    public static ServiceIdContainer getServiceIdContainer() {
        var localRef = serviceIdContainer;
        if (localRef == null) {
            synchronized (RegisterContainer.class) {
                localRef = serviceIdContainer;
                if (localRef == null) {
                    serviceIdContainer = localRef = new ServiceIdContainer();
                }
            }
        }
        return localRef;
    }

}
