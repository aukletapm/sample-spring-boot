package com.aukletapm.go.sample.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Eric Xu
 * @date 01/03/2018
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

