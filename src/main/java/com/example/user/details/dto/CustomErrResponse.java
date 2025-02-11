package com.example.user.details.dto;

import java.time.Instant;

public class CustomErrResponse {

    private String message;
    private Instant timestamp;

    public CustomErrResponse(String message) {
        this.message = message;
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
