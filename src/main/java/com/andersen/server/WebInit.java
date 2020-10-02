package com.andersen.server;

import com.andersen.config.AppConfig;
import com.andersen.config.DatabaseConfig;
import com.andersen.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        rootContext.register(DatabaseConfig.class);
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(rootContext);
        container.addListener(contextLoaderListener);

        AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
        servletContext.register(WebConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletContext);
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

    }

}