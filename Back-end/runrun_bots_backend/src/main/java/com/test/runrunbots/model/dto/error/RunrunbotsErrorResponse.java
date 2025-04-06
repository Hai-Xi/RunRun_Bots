package com.test.runrunbots.model.dto.error;


public record RunrunbotsErrorResponse(String exceptionMessage, String errorFrom, String exceptionDetails) {


    public RunrunbotsErrorResponse(Exception e) {
        this("An error occurred", e.getMessage(), null);
    }
}
