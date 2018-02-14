package com.ds.spring.spark.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by ds on 12/28/2017.
 */
//@Component
//@Configuration
//@PropertySource("classpath:application.properties")
//application.properties
public class ApplicationProperties {

    @Autowired
    private Environment envr;


    public ApplicationProperties(){
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationProperties.class);

        Environment env = (Environment) context.getEnvironment();

        System.out.println("url: " +env.getProperty("spring.datasource.url"));

    }
//@Bean
    public void printProp( ){
        System.out.println("url: ");//+ envr.getProperty("server.port")
    }
}
