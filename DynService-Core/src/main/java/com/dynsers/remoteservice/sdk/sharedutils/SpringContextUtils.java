package com.dynsers.remoteservice.sdk.sharedutils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringContextUtils {

    @Getter
    @Setter
    private static ApplicationContext context;

    public static <T> Collection<T> getBeans(Class<T> type) {
        return context.getBeansOfType(type).values();
    }
}