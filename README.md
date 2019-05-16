# SpringCloud项目创建

## 1、idea创建maven项目

### 	1.1、删除src等文件

### 	1.2、编写pom文件，设置全局依赖版本

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zei.happy</groupId>
    <artifactId>happy-cloud</artifactId>
    <version>1.0-SNAPSHOT</version>

    <modules>
        <module>config8001</module>


    </modules>
    
    <!--配置springboot-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <!--配置参数-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
        <spring-boot.version>2.1.3.RELEASE</spring-boot.version>
        <mysql.version>8.0.15</mysql.version>
        <druid.version>1.1.10</druid.version>
        <mybatis.version>2.0.0</mybatis.version>
        <redis.version>2.1.3.RELEASE</redis.version>
        <lombok.version>1.18.8</lombok.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-redis</artifactId>
                <version>${redis.version}</version>
            </dependency>
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
                <scope>provided</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <finalName>happy-cloud</finalName>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <configuration>
                    <delimiters>
                        <delimit>$</delimit>
                    </delimiters>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## 2、创建config项目

### 	2.1、编写application.yml并提交到github上

```yml
spring:
  profiles:
    active:
    - dev
---
spring:
  profiles: dev
  application:
    name: happy-config-dev
---
spring:
  profiles: test
  application:
    name: happy-config-test
```

### 	2.2、创建spring工程 config 

#### 		2.2.1、pom文件指向父模块

#### 		2.2.2、引入config-server依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.zei.happy</groupId>
		<artifactId>happy-cloud</artifactId>
	</parent>
	<groupId>com.zei.happy</groupId>
	<artifactId>config8001</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>config8001</name>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>

	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```

#### 		2.2.3、创建bootstrap.yml文件

```yml
server:
  port: 8001
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/zzei/happy-config.git
```

#### 		2.2.4、主启动类上加上**@EnableConfigServer**注解

```java
@SpringBootApplication
@EnableConfigServer
public class Config8001Application {
	public static void main(String[] args) {
		SpringApplication.run(Config8001Application.class, args);
	}
}
```

## 3、创建eureka项目

### 	3.1、编写config-eureka文件并提交到github

```yml
spring:
  profiles:
    active:
    - 7001

---
spring:
  profiles: 7001
server:
  port: 7001
eureka:
  instance:
    hostname: localhost
    #hostname: eureka7001.com   #集群配置
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
```

### 	3.2、创建eureka工程 eureka7001

#### 		3.2.1、pom文件指向父模块

#### 		3.2.2、引入config、eureka-server

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.zei.happy</groupId>
		<artifactId>happy-cloud</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<groupId>com.zei.happy</groupId>
	<artifactId>eureka7001</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>eureka7001</name>


	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

#### 		3.2.3、创建bootstrap.yml

```yml
spring:
  cloud:
    config:
      name: config-eureka
      label: master
      uri: http://localhost:8001
      profile: 7001
```

#### 		3.2.4、主启动类上添加**@EnableEurekaServer**注解

## 4、创建服务端provide

### 	4.1、pom文件指向父模块

### 	4.2、pom文件引入所需依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.zei.happy</groupId>
		<artifactId>happy-cloud</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<groupId>com.zei.happy</groupId>
	<artifactId>provide8002</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>provide8002</name>

	<dependencies>
		<!--common-->
		<dependency>
			<groupId>com.zei.happy</groupId>
			<artifactId>common</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>
		<!--redis-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		<!--data-->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
		</dependency>
		<!--rabbitmq-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
		<!--熔断器-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```

### 	4.3、配置application.yml

```yml
server:
  port: 8002
spring:
  application:
    name: provide8002
# 配置数据
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://10.211.55.7:3306/happy
    username: root
    password: 123456
    type: com.alibaba.druid.pool.DruidDataSource
# 配置redis sentinel集群
  redis:
    database: 0
    host: 10.211.55.7
    port: 6379
    password: 123456
    sentinel:
      master: mymaster
      nodes: 10.211.55.7:26379,10.211.55.7:26380,10.211.55.7:26381
    jedis:
      pool:
        max-active: 300
        max-wait: -1
        max-idle: 100
        min-idle: 20
# 配置rabbitmq
  rabbitmq:
    host: 10.211.55.7
    username: guest
    password: guest
    listener:
      simple:
        concurrency: 10
        max-concurrency: 20
        prefetch: 5
#配置mybatis驼峰转换
mybatis:
  configuration:
    map-underscore-to-camel-case: true
#配置eureka服务注册
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka/
  instance:
    instance-id: provide8002
    prefer-ip-address: true
info:
  author: zzei
  server:
    name: provide8002

```

### 	4.4、启动类添加对应标签

​		**@EnableEurekaClient**

​		**@MapperScan("com.zei.happy.mapper")**

### 	4.5、配置hystrix

#### 		4.5.1、启动类上添加**@EnableCircuitBreaker**

#### 		4.5.2、controller中使用fallback

```java
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

```

## 5、创建客户端consumer

### 	5.1、pom文件指向父模块

### 	5.2、pom文件引入所需依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.zei.happy</groupId>
		<artifactId>happy-cloud</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<groupId>com.zei.happy</groupId>
	<artifactId>consumer8003</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>consumer8003</name>

	<dependencies>
		<!--common-->
		<dependency>
			<groupId>com.zei.happy</groupId>
			<artifactId>common</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
		<!--ribbon-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
		</dependency>
		<!--feign-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```

### 	5.3、启动类上添加所需注解

​		**@EnableEurekaClient**

### 	5.4、controller使用restful调用provide

```java
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

```

### 	5.3、使用ribbon负载均衡方式

​		在RestTemplate配置类上添加注解**@LoadBalanced**

```java
package com.zei.happy.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

```

### 	5.4、使用feign负载均衡方式

​		controller调用feignService

```java
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

```

​		feignService 调用provide

```java
package com.zei.happy.service;

import com.zei.happy.domain.JsonData;
import com.zei.happy.service.impl.FeignServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//feign
@FeignClient("PROVIDE8002")
FeignServiceFallbackFactory.class)
public interface FeignService {

    @GetMapping(value = "/test")
    JsonData test();
}

```

### 	5.5、feign加hystrix 客户端熔断机制

```java
package com.zei.happy.service;

import com.zei.happy.domain.JsonData;
import com.zei.happy.service.impl.FeignServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//feign+hystrix
@FeignClient(value = "PROVIDE8002",fallbackFactory = FeignServiceFallbackFactory.class)
public interface FeignService {

    @GetMapping(value = "/test")
    JsonData test();
}


```

​		FeignServiceFallbackFactory统一处理接口方法

```java
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

```

## 6、创建zuul网关服务

### 	6.1、pom文件指向父模块

### 	6.2、pom文件引入所需依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.zei.happy</groupId>
		<artifactId>happy-cloud</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<groupId>com.zei.happy</groupId>
	<artifactId>zuul8004</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>zuul8004</name>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
		</dependency>

	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```

### 	6.3、启动类上添加所需注解

​		**@EnableEurekaClient**

​		**@EnableZuulProxy**

### 	6.4、添加配置文件

```yml
server:
  port: 8004
spring:
  application:
    name: zuul8004
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka/
  instance:
    instance-id: zuul8004
    prefer-ip-address: true
info:
  author: zzei
  server:
    name: zuul8004
zuul:
  prefix: /                     #统一前缀
  ignored-services: "*"
  routes:
    provide:                    #路由微服务
      serviceId: provide8002    #微服务application.name 若该服务有多个，则自动采用轮询负载均衡
      path: /provide/**         #路由路径
    consumer:
      serviceId: consumer8003
      path: /consumer/**

```

