package com.example.simpletwiter_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
public class SimpleTwiterBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleTwiterBeApplication.class, args);
    }

}
