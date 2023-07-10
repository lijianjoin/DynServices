/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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
