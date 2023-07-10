package com.dynsers.demo.remoteservice.serviceprovider.testrestcontroller;

import com.dynsers.demo.remoteservice.serviceprovider.testservice.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("api")
public class TestRestController {


    @Autowired
    private TestService2 testService2;

    @GetMapping("/test/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> testGet() {
 //       simpleService.function1();
        final String result = "asdf";

        testService2.testFunc();

        return ResponseEntity.ok(result);
    }

}
