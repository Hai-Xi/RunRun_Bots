package com.test.runrunbots.model;


public record RunrunbotsErrorResponse(String exceptionMessage, String errorFrom, String exceptionDetails) {


    public RunrunbotsErrorResponse(Exception e) {
        this("An error occurred", e.getMessage(), null);
    }
}
