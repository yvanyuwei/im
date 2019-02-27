package com.vm.im;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.vm.im"})
@MapperScan({"com.vm.im.dao"})
@EnableScheduling
@EnableAsync
public class VmImApplication {

    public static void main(String[] args) {
        SpringApplication.run(VmImApplication.class, args);
    }

}

