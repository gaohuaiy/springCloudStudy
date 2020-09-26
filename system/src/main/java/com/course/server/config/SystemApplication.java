package com.course.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.course.system.mapper")
public class SystemApplication {
    private static final Logger Log = LoggerFactory.getLogger(SystemApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SystemApplication.class);
        ConfigurableEnvironment environment = springApplication.run(args).getEnvironment();
        Log.info("启动成功！");
        Log.info("system 项目地址：\t http://127.0.0.1:{} ",environment.getProperty("server.port"));
    }

}
