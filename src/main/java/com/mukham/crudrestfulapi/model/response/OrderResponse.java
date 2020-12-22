package com.mukham.crudrestfulapi.model.response;

public class OrderResponse {

    Status status;
    Object data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
