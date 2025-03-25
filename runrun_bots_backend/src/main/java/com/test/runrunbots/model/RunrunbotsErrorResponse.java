package com.test.runrunbots.model;


public record RunrunbotsErrorResponse(String exceptioMessage, String errorFrom, String exceptionDetails) {


    public RunrunbotsErrorResponse(Exception e) {
        this("An error occurred", e.getMessage(), null);
    }
}
