package com.dynsers.core.config;

import com.dynsers.core.configuration.RSPropertyResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRSPropertyResolver {



    @Test
    void testGetPropertyValueWithPruePlaceholder() {
        String result = RSPropertyResolver.getPropertyValue("${testYaml.testKey}");
        assertEquals("1", result);
    }

    @Test
    void testGetPropertyValueWithoutPlaceholder() {
        String result = RSPropertyResolver.getPropertyValue("testYaml.testKey");
        assertEquals("testYaml.testKey", result);
    }

    @Test
    void testGetPropertyValueWithPlaceholder() {
        String result = RSPropertyResolver.getPropertyValue("abc ${testYaml.testKey}");
        assertEquals("abc 1", result);
    }

}
