package com.dynsers.demo.remoteservice.spring;


import com.dynsers.demo.remoteservice.spring.services.TestService;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan(basePackages = {"com.dynsers.demo.remoteservice.spring"})
public class App 
{

    @Autowired
    private TestService t;

    public static void main( String[] args ) throws NoSuchFieldException, IllegalAccessException {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        TestService t = (TestService) context.getBean("RemoteServiceTest");


        RemoteServiceId id = new RemoteServiceId();
        id.setGroupId("com.dynsers.dynservice");
        id.setResourceId("testService");
        id.setResourceVersion("0.0.1");
        id.setServiceId("com.dynsers.demo.dynservice.demoapi.api.TestInterface");
        id.setServiceVersion("0.0.1");
        id.setUuid("c8c7b30d-5f4c-4fde-ba57-6fb75be98ffc");
        //id.setUuid("c8c7b30d-5f4c-4fde-ba57-6fb75be98ffd");

        RemoteServiceManager.getInstance().configRemoteService(t, "testInterface", id);
        t.test();
    }
}
