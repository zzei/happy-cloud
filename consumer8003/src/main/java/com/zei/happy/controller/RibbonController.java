package com.zei.happy.controller;

import com.zei.happy.domain.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {

    private static final String RESTFUL_URL = "http://PROVIDE8002";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/test")
    public JsonData test(){

        return restTemplate.getForObject(RESTFUL_URL+"/test",JsonData.class);
    }
}
