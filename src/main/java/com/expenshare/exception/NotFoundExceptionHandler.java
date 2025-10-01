package com.expenshare.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = { NotFoundException.class, ExceptionHandler.class })
public class NotFoundExceptionHandler implements ExceptionHandler<NotFoundException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, NotFoundException exception) {
        // Standard JSON error response
        return HttpResponse.notFound(new ErrorResponse("NOT_FOUND", exception.getMessage()));
    }

    // Inner class or a dedicated DTO in `model/dto/common/`
    public static class ErrorResponse {
        public String code;
        public String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
