package com.zei.happy.controller;

import com.zei.happy.domain.JsonData;
import com.zei.happy.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    FeignService feignService;

    @GetMapping("/feign")
    public JsonData test(){
        return feignService.test();
    }
}
