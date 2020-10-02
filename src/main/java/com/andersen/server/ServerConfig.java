package com.andersen.server;

import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.andersen.server"})
@PropertySource("classpath:server.properties")
public class ServerConfig {

    @Bean
    public Tomcat tomcat() {
        return new Tomcat();
    }
}