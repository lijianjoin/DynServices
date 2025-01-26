package com.dynsers.dynservice.demo.service;

import com.dynsers.demo.dynservice.demoapi.api.TestInterface;
import com.dynsers.demo.dynservice.demoapi.api.UnknowParameterException;
import com.dynsers.demo.dynservice.demoapi.data.Address;
import com.dynsers.demo.dynservice.demoapi.data.ComplexParameter;
import com.dynsers.remoteservice.annotations.ServiceProvider;
import org.springframework.stereotype.Service;

@Service
@ServiceProvider(serviceName = "TestInterfaceImpl", version = "1.0.0", uuid = "c6dadd03-a506-4d68-8c88-ad99baec47e5")
public class TestInterfaceImpl implements TestInterface {

    @Override
    public String getSampleString() {
        return "Here is the string from the remote service";
    }

    @Override
    public String testIncomingMethod(String input) {
        System.out.println("The input is: " + input);
        return "";
    }

    @Override
    public String testMethodWithComplexParameter(ComplexParameter complexParameter) {
        return "function finish";
    }

    @Override
    public String getSampleStringWithExcept() throws UnknowParameterException {
        return "Here is the string from the remote service maybe the exception generated";
    }

    @Override
    public ComplexParameter getPersonInfo(int id) {
        var res = new ComplexParameter(
                "Tester", 18, new Address("Jakob", "34", "Gö", "Ni", "DE", "38440"), "tester@test.com", "1234567890");
        return res;
    }
}
