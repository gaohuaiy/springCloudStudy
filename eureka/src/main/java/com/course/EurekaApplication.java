package com.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

   /* public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }*/
    private static final Logger Log = LoggerFactory.getLogger(EurekaApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EurekaApplication.class);
        ConfigurableEnvironment environment = springApplication.run(args).getEnvironment();
        Log.info("启动成功！");
        Log.info("Eureka 项目地址：\t http://127.0.0.1:{} ",environment.getProperty("server.port"));
    }

}
