package com.dynsers.remoteservice.sdk.config;

import com.dynsers.remoteservice.sdk.configuration.RSYamlResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestRSYamlResolver {

    @Test
    void testGetInstance(){
        RSYamlResolver resolver1 = RSYamlResolver.getInstance();
        RSYamlResolver resolver2 = RSYamlResolver.getInstance();
        assertEquals(resolver2, resolver1);
    }

    @Test
    void testLoadYamlFile(){
        RSYamlResolver resolver1 = RSYamlResolver.getInstance();
        resolver1.loadYamlFile("");
        String value = resolver1.getValueAsString("testYaml.testKey");
        assertNotNull(value);
    }

    @Test
    void testLGetStringValue(){
        RSYamlResolver resolver1 = RSYamlResolver.getInstance();
        resolver1.loadYamlFile("");
        String value = resolver1.getValueAsString("testYaml.testKey");
        assertEquals("1", value);
    }

    @Test
    void testLGetWithWrongKey(){
        RSYamlResolver resolver1 = RSYamlResolver.getInstance();
        resolver1.loadYamlFile("");
        String value = resolver1.getValueAsString("testYaml.testKey1");
        assertEquals("", value);
    }

}
