package com.andersen;

import com.andersen.server.ServerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppLauncher {

    public static void main(String[] ars) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ServerConfig.class);
        ctx.refresh();
        ctx.start();
    }

}
