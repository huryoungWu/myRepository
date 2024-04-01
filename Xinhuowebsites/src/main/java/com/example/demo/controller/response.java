package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class response {

    @RequestMapping("/res")
    public List<String> res(){

        List<String> messages = new ArrayList<>();
        messages.add("狂神说java");
        messages.add("狂神说web");
        messages.add("狂神说ui");

        return messages;


    }
}
