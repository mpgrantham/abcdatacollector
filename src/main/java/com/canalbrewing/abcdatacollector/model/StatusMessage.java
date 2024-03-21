package com.canalbrewing.abcdatacollector.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusMessage {
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    private String status;
    private String message;

    public StatusMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "StatusMessage [message=" + message + ", status=" + status + "]";
    }
}
