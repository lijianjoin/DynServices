package com.dynsers.demo.dynservice.demoapi.api;

public interface TestInterface {

    public String getSampleString();

    public String getSampleStringWithExcept() throws UnknowParameterException;
}
