package com.suncontrol;

import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@MapperScan({"com.suncontrol.core.repository", "com.suncontrol.mapper"})
@EnableScheduling
public class SunControlApplication {

    @PostConstruct
    public void init() {
        // 서버 시계가 무엇으로 되어 있든, 이 어플리케이션은 한국 시간으로 동작함
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SunControlApplication.class, args);
    }
}