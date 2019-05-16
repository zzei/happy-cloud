package com.zei.happy.service;

import com.zei.happy.domain.JsonData;
import com.zei.happy.service.impl.FeignServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//feign
//@FeignClient("PROVIDE8002")
//feign+hystrix
@FeignClient(value = "PROVIDE8002",fallbackFactory = FeignServiceFallbackFactory.class)
public interface FeignService {

    @GetMapping(value = "/test")
    JsonData test();
}
