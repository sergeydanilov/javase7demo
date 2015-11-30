package ru.bpal.reminder.rest;

/**
 * Created by serg on 28.10.15.
 */
public class ResponseError {

    private String message;

    public ResponseError(String message, String... args) {
        this.message = String.format(message, args);
    }

    public ResponseError(Exception e) {
        this.message = e.getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}
