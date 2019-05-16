package com.zei.happy.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zei.happy.domain.JsonData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @HystrixCommand(fallbackMethod = "fallbackTest")
    public JsonData test(){
        if(true){
            throw new RuntimeException("null");
        }
        return new JsonData(200,"good","yes");
    }

    public JsonData fallbackTest(){

        return new JsonData(500,"error","no");
    }
}
