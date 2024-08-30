package com.yuzarsif.gameservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("demo")
public class Demo {

    @GetMapping
    public String demo() {
        return "Hello World";
    }
}
