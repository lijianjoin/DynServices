/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.sdk.sdk.configuration;

import com.dynsers.remoteservice.sdk.sdk.sharedutils.ApplicationContextProvider;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Static util class to resolve property values from spring environment
 * <p>
 * Constructor is private because no instance of this class is needed
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemoteServicePropertyResolver {

    public static String getPropertyValue(String keyString) {
        final var regex = "\\$\\{(.+?)\\}";
        var result = keyString;
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(keyString);
        var output = new StringBuilder();
        while (matcher.find()) {
            String replacement = getProperty(matcher.group(1));
            matcher.appendReplacement(output, replacement);
        }
        if (StringUtils.isNotEmpty(output.toString())) {
            result = output.toString();
        }
        return result;
    }

    /**
     * Receiving application properties from spring environment
     *
     * @param key property key
     * @return property value
     */
    @Nonnull
    private static String getProperty(String key) {
        var env = ApplicationContextProvider.getApplicationContext().getEnvironment();
        return env.getProperty(key, "");
    }
}
