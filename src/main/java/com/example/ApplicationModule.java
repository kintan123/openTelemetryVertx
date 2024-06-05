package com.example;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

//import in.hopscotch.consul.di.provider.ConsulClientProvider;
//import in.hopscotch.consul.di.provider.MyConsulClient;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

public class ApplicationModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationModule.class);

//    @Override
//    protected void configure() {
//        VertxOptions vertxOptions = new VertxOptions().setMaxWorkerExecuteTime((long) 1.8e+12); //30 mins in nanoseconds
//        Vertx vertx = Vertx.vertx(vertxOptions);
//        bind(Vertx.class).toInstance(vertx);
//        bind(MyConsulClient.class).toProvider(ConsulClientProvider.class).in(Singleton.class);
//
//        bind(EventBus.class).toInstance(vertx.eventBus());
//    }

}