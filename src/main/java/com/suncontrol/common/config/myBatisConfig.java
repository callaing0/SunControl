package com.suncontrol.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.suncontrol.domain")
public class myBatisConfig {
}
