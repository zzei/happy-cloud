package com.zei.happy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.zei.happy")
public class Consumer8003Application {

	public static void main(String[] args) {
		SpringApplication.run(Consumer8003Application.class, args);
	}

}
