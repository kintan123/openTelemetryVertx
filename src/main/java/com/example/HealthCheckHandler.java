package com.example;

import javax.inject.Singleton;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@Singleton
public class HealthCheckHandler extends AbstractHttpHandler {

    public void healthCheck(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json");

        // Check health condition here, for example:
        boolean isHealthy = true;

        if (isHealthy) {
            response.setStatusCode(200).end("{\"status\": \"ok\"}");
        } else {
            response.setStatusCode(503).end("{\"status\": \"unhealthy\"}");
        }
    }
}
