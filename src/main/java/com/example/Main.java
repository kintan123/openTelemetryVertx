package com.example;

import io.vertx.core.Vertx;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Injector injector = createInjector();
        HttpVerticle httpVerticle = injector.getInstance(HttpVerticle.class);
        vertx.deployVerticle(httpVerticle);
    }

    protected static Injector createInjector() {
        return Guice.createInjector(Stage.PRODUCTION, getModules());
    }

    protected static List<Module> getModules() {
        List<Module> modules = new ArrayList<>();
        modules.add(new ApplicationModule());
        return modules;
    }
}
