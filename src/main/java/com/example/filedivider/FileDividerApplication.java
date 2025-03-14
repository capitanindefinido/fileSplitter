
package com.example.filedivider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FileDividerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileDividerApplication.class, args);
    }
}
