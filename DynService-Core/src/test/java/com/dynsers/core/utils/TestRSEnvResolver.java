package com.dynsers.core.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
public class TestRSEnvResolver {
    @Autowired
    private YamlFooProperties yamlFooProperties;

    @Test
    void testGetEnvValue() {
        String value = System.getenv("remoteService.serviceProvider.groupId");
        System.out.println(value);
    }

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        assertEquals("Tets", yamlFooProperties.getName());
    }
}
