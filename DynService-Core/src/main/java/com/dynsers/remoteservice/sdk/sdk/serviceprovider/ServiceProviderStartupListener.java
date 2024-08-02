/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.sdk.serviceprovider;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration("serviceProviderStartupListener")
@ComponentScan({"com.dynsers.remoteservice.sdk.sdk.serviceprovider",
    "com.dynsers.remoteservice.sdk.sdk.sharedutils",
    "com.dynsers.remoteservice.sdk.sdk.configuration"})
public class ServiceProviderStartupListener {

}
