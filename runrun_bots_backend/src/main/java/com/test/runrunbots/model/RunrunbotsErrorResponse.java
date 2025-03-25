package com.test.runrunbots.model;


public record RunrunbotsErrorResponse(String message, String errorFrom, String details) {


    public RunrunbotsErrorResponse(Exception e) {
        this("An error occurred", e.getMessage(), null);
    }
}
