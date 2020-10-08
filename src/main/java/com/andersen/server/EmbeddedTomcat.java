package com.andersen.server;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EmbeddedTomcat {

    @Value("${tomcat.port:8080}")
    private String port;
    @Value("${tomcat.tempDir:tomcat}")
    private String tempDir;

    private final Tomcat tomcat;

    @PostConstruct
    public void start() throws Exception {

        System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");

        String appBase = ".";
        tomcat.setBaseDir(createTempDir());
        tomcat.setPort(Integer.parseInt(port));
        tomcat.getHost().setAppBase(appBase);
        Context context = tomcat.addWebapp("", appBase);
        StandardJarScanner jarScanner = (StandardJarScanner) context.getJarScanner();
        jarScanner.setScanManifest(false);
        tomcat.start();
        tomcat.getServer().await();

        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName("springSecurityFilterChain");
        filterDef.setFilterClass("org.springframework.web.filter.DelegatingFilterProxy");
        context.addFilterDef(filterDef);

        FilterMap filterMapping = new FilterMap();
        filterMapping.setFilterName("springSecurityFilterChain");
        filterMapping.addURLPattern("/api/**");
        context.addFilterMap(filterMapping);
    }


    private String createTempDir() {
        try {
            File tmp = File.createTempFile(tempDir, "." + port);
            tmp.delete();
            tmp.mkdir();
            tmp.deleteOnExit();
            return tmp.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    @PreDestroy
    public void stop() throws LifecycleException {
        tomcat.stop();
    }

}