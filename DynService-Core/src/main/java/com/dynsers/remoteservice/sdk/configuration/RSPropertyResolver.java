package com.dynsers.remoteservice.sdk.configuration;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RSPropertyResolver {

    private static RSYamlResolver yamlResolver = null;

    private static String regex = "\\$\\{(.+?)\\}";
    public static String getPropertyValue(String keyString) {
        String result = keyString;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(keyString);
        StringBuffer output = new StringBuffer();
        while (matcher.find()) {
            String replacement = getProperty(matcher.group(1));
            matcher.appendReplacement(output, replacement);
        }
        if(StringUtils.isNotEmpty(output.toString()))
            result = output.toString();
        return result;
    }

    private static String getProperty(String key) {
        return getYamlResolver().getValueAsString(key);
    }

    private static RSYamlResolver getYamlResolver(){
        if(null == yamlResolver) {
            yamlResolver = RSYamlResolver.getInstance();
            yamlResolver.loadYamlFile("");
        }
        return yamlResolver;
    }

}
