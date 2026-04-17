package com.suncontrol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"com.suncontrol.core.repository", "com.suncontrol.mapper"})
@EnableScheduling
public class SunControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunControlApplication.class, args);
    }
}