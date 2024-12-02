/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.sdk.serviceprovider;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("serviceProviderStartupListener")
@ComponentScan({
    "com.dynsers.remoteservice.sdk.sharedutils",
    "com.dynsers.remoteservice.sdk.serviceprovider",
    "com.dynsers.remoteservice.sdk.configuration"
})
public class ServiceProviderStartupListener {}
