package com.lenovo.topic11.bean;

import java.util.List;

public class ResultMessage_CreateProductionLine {

    /**
     * status : 200
     * message : 创建学生生产线成功
     * data : []
     */

    public int status;
    public String message;
    public List<?> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultMessage_CreateProductionLine{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
