package com.test.runrunbots.model;


public record RunrunbotsErrorResponse(String message, String error, String details) {


    public RunrunbotsErrorResponse(Exception e) {
        this("An error occurred", e.getMessage(), null);
    }
}
