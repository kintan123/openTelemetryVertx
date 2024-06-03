package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(HttpVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        router.get("/supply-service/health-check").handler(ctx -> {
            logger.info("Health check request received");
            ctx.response().putHeader("content-type", "application/json").end("{\"status\":\"UP\"}");
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8082, http -> {
            if (http.succeeded()) {
                startFuture.complete();
                logger.info("HTTP server started on port 8082");
            } else {
                startFuture.fail(http.cause());
            }
        });
    }

}
