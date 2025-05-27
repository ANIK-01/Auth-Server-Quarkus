package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusMessage<T> {
    private final boolean success;
    private final String message;
    private final T data;

    // Constructor for success with data (e.g., created user)
    public StatusMessage(T data) {
        this.success = true;
        this.message = null;
        this.data = data;
    }

    // Constructor for success without data (e.g., deletion)
    public StatusMessage(boolean success) {
        this.success = success;
        this.message = null;
        this.data = null;
    }

    // Constructor for error (e.g., not found)
    public StatusMessage(String message) {
        this.success = false;
        this.message = message;
        this.data = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}