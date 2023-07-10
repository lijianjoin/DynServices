package com.dynsers.remoteservice.sdk.configuration;

import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceManager;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class RSYamlResolver {

    private final static String DEFUALT_YAML_FILE = "application.yml";
    private Yaml yaml;

    Map<String, Object> values = null;

    private static RSYamlResolver instance;

    private RSYamlResolver(){

    }

    public static RSYamlResolver getInstance(){
        RSYamlResolver localRef = instance;
        if (localRef == null) {
            synchronized (RemoteServiceManager.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new RSYamlResolver();
                }
            }
        }
        return localRef;
    }

    public void loadYamlFile(String file) {
        String filename = "";
        if(StringUtils.isEmpty(file)) {
            filename = RSYamlResolver.DEFUALT_YAML_FILE;
        }
        this.yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        values = yaml.load(inputStream);
    }

    public String getValueAsString(String keyIn) {
        String[] keys = keyIn.split("\\.");
        Map tempMap = values;
        String res = "";
        for(String key : keys) {
            Object tempValue = tempMap.get(key);
            if(tempValue == null) {
                res = "";
                break;
            }
            if(tempValue instanceof Map) {
                tempMap = (Map) tempValue;
            }
            else {
                res = tempValue.toString();
            }
        }
        return res;

    }
}
