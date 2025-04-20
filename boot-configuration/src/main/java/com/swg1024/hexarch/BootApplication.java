package com.swg1024.hexarch;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.swg1024.hexarch.persistadapter")
class BootApplication {


    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
        log.info("==================>started!");
    }
}
