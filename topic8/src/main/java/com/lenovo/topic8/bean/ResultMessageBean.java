package com.lenovo.topic8.bean;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic6.bean
 * @ClassName: ResultMessageBean
 * @CreateDate: 2020/1/19 11:36
 */
public class ResultMessageBean {

    /**
     * status : 200
     * message : 创建学生生产线成功
     * data : []
     */

    private int status;
    private String message;
    private List<?> data;

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

    @NonNull
    @Override
    public String toString() {
        return "ResultMessageBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
