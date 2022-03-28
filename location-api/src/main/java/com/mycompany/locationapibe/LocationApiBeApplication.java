package com.mycompany.locationapibe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@OpenAPIDefinition
@SpringBootApplication
public class LocationApiBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationApiBeApplication.class, args);
    }

}
