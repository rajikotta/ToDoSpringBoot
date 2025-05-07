package com.raji.todo;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    public String hello(){
        return "Hello World";
    }
}
