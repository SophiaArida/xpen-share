package com.expenshare.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse<?>> {
    @Override
    public HttpResponse<?> handle(HttpRequest request, Exception exception) {
        if (exception instanceof NotFoundException) {
            return HttpResponse.notFound(new ErrorResponse("NOT_FOUND", exception.getMessage()));
        } else if (exception instanceof ConflictException) {
            return HttpResponse.status(HttpStatus.CONFLICT).body(new ErrorResponse("CONFLICT", exception.getMessage()));
        } else {
            return HttpResponse.serverError(new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred"));
        }
    }

    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
