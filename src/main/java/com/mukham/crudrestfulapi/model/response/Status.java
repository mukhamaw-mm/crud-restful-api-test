package com.mukham.crudrestfulapi.model.response;

public class Status {
    String status;
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
