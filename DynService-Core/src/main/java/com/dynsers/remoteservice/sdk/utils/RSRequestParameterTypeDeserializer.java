package com.dynsers.remoteservice.sdk.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RSRequestParameterTypeDeserializer extends StdDeserializer<Class<?>[]> {

    public RSRequestParameterTypeDeserializer() {
        this(null);
    }

    protected RSRequestParameterTypeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Class<?>[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws JsonProcessingException, IOException {
        Class<?>[] res = new Class<?>[0];
        String names = jsonParser.getText();
        List<Class<?>> resList = new ArrayList<Class<?>>();
        if(StringUtils.isNotEmpty(names)) {
            String[] clsNames = names.split(";");
            Arrays.stream(clsNames).forEach(val -> {
                if(StringUtils.isNotEmpty(val)) {
                    try {
                        resList.add(Class.forName(val));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }});
            res = new Class[resList.size()];
            int pos = 0;
            for(Class<?> cls : resList) {
                res[pos] = cls;
                pos++;
            }}
        return res;
    }
}
