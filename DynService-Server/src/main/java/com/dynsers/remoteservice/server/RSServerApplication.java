package com.dynsers.remoteservice.server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.dynsers.remoteservice.server")
public class RSServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(RSServerApplication.class, args);
    }
}
