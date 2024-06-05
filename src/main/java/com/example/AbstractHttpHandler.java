package com.example;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpServerResponse;

public class AbstractHttpHandler {

    public static void sendError(HttpResponseStatus internalServerError, HttpServerResponse response, String cause) {
        response.setStatusCode(internalServerError.code()).end(cause);
    }
}