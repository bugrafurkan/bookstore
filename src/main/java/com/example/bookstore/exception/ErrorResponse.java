package com.example.bookstore.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    // Getters and Setters
    private int status;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
