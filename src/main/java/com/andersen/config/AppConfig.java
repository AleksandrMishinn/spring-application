package com.andersen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.andersen.service", "com.andersen.converter", "com.andersen.domain", "com.andersen.config"})
public class AppConfig {
}
