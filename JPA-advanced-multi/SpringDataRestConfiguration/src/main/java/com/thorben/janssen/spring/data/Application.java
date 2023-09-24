package com.thorben.janssen.spring.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy.RepositoryDetectionStrategies;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // @Bean
    // public RepositoryRestConfigurer repositoryRestConfigurer() {

    //     return new RepositoryRestConfigurer() {

    //         @Override
    //         public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
    //             config.setBasePath("/api");
    //             config.setRepositoryDetectionStrategy(RepositoryDetectionStrategies.ANNOTATED);
    //         }
    //     };
    // }
}