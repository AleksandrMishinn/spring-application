package com.andersen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackages = {"com.andersen.service", "com.andersen.converter", "com.andersen.domain"})
@PropertySource("classpath:application.properties")
public class AppConfig {
}
