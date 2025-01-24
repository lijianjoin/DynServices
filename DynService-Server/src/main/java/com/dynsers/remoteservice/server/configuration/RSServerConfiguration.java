/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.server.configuration;

import com.dynsers.remoteservice.sdk.configuration.RemoteServiceProviderConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RSServerConfiguration extends RemoteServiceProviderConfig {
}
