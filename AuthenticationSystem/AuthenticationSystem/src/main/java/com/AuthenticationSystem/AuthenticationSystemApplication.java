package com.AuthenticationSystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.AuthenticationSystem.mapper")
public class AuthenticationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run ( AuthenticationSystemApplication.class , args );
    }

}
