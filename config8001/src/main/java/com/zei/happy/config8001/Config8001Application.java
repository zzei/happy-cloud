package com.zei.happy.config8001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Config8001Application {

	public static void main(String[] args) {
		SpringApplication.run(Config8001Application.class, args);
	}

}
