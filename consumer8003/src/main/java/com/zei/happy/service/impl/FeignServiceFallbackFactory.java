package com.zei.happy.service.impl;

import com.zei.happy.domain.JsonData;
import com.zei.happy.service.FeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignServiceFallbackFactory implements FallbackFactory<FeignService>{

    @Override
    public FeignService create(Throwable throwable) {
        return new FeignService() {
            @Override
            public JsonData test() {
                return new JsonData(500,"feign do","no");
            }
        };
    }
}
