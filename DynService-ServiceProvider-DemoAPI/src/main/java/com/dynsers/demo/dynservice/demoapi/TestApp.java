package com.dynsers.demo.dynservice.demoapi;

import com.dynsers.demo.dynservice.demoapi.test.SecuredMethod;

public class TestApp {

    public static void main(String[] args) {

        SecuredMethod sm = new SecuredMethod();

        sm.lockedMethod();
        sm.unlockedMethod();
    }
}
