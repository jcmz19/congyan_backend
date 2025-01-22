package com.congyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MyProjectBackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MyProjectBackendApplication.class, args);

    }

}
