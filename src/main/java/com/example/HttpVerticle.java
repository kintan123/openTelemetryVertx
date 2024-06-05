package com.example;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

public class HttpVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(HttpVerticle.class);
    private final HealthCheckHandler healthCheckHandler;
    private WebClient httpClient = null;

    @Inject
    public HttpVerticle(HealthCheckHandler healthCheckHandler) {
        this.healthCheckHandler = healthCheckHandler;
    }

//    @Override
//    public void start(Future<Void> startFuture) throws Exception {
//        Router router = Router.router(vertx);
//
//        router.get("/supply-service/health-check").handler(ctx -> {
//            logger.info("Health check request received");
//            ctx.response().putHeader("content-type", "application/json").end("{\"status\":\"UP\"}");
//        });
//
//        vertx.createHttpServer().requestHandler(router::accept).listen(8082, http -> {
//            if (http.succeeded()) {
//                startFuture.complete();
//                logger.info("HTTP server started on port 8082");
//            } else {
//                startFuture.fail(http.cause());
//            }
//        });
//    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        HttpServer server = vertx.createHttpServer();
        httpClient = WebClient.create(vertx);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().handler(LoggerHandler.create(true, LoggerFormat.DEFAULT));
        router.route("/*").handler(this::routeHandler).failureHandler(this::routeFailureHandler);

        initRoutes(router);
        Router mainRouter = Router.router(vertx);
        mainRouter.route("/*");
        mainRouter.mountSubRouter("/supply-service", router);
        server.requestHandler(mainRouter::accept).listen(8083, ar -> {
            if (ar.succeeded()) {
                logger.info("HTTP server running on port " + 8083);
                startFuture.complete();
            } else {
                logger.error("Could not start a HTTP server", ar.cause());
                startFuture.fail(ar.cause());
            }
        });
    }

    private void initRoutes(Router router) {
        router.get("/health-check").handler(healthCheckHandler::healthCheck);
    }

    private void routeHandler(RoutingContext routingContext) {
        routingContext.put("webClient", httpClient);
        routingContext.next();
    }

    private void routeFailureHandler(RoutingContext routingContext) {
        if (routingContext.failed()) {
            Throwable e = routingContext.failure();
            logger.error(e.getMessage(), e);
            AbstractHttpHandler.sendError(HttpResponseStatus.INTERNAL_SERVER_ERROR, routingContext.response(), e.getMessage());
        }
    }

}
