package com.dynsers.demo.dynservice.demoapi;

import com.dynsers.demo.dynservice.demoapi.test.Secured;
import com.dynsers.demo.dynservice.demoapi.test.SecuredMethod;
import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void dummyTest4(){
        System.out.println("Test 4");
    }

    @Test
    public void dummyTest5(){
        System.out.println("Test 5");
        new SecuredMethod().lockedMethod();
    }
}