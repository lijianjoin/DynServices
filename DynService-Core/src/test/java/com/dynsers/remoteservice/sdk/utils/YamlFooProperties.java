package com.dynsers.remoteservice.sdk.utils;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@Data
public class YamlFooProperties {

    private String name;

    private List<String> aliases;
}
