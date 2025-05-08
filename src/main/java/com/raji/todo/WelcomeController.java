package com.raji.todo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("welcome")
    public String hello(){
        return "Hello World";
    }
}
