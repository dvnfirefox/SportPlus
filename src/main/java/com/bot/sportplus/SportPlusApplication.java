package com.bot.sportplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@EntityScan(basePackages = "com.bot.sportplus.model")
@SpringBootApplication
public class SportPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportPlusApplication.class, args);
    }

}
