package com.xicheng.wxchart.controller;


import com.xicheng.wxchart.util.HttpClientRes;
import com.xicheng.wxchart.util.HttpClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("hello")
    public String hello(){



        return "helloworld";
    }
}
