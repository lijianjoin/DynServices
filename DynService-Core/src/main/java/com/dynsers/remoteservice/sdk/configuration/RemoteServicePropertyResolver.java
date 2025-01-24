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

package com.dynsers.remoteservice.sdk.configuration;

import com.dynsers.remoteservice.sdk.sharedutils.SpringContextUtils;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;

import java.util.regex.Pattern;

@DependsOn("applicationContextProvider")
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
        var env = SpringContextUtils.getContext().getEnvironment();
        return env.getProperty(key, "");
    }
}
