package com.lenovo.topic15.bean;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic15.bean
 * @ClassName: ProductionLineResultMessage
 * @CreateDate: 2020/2/16 13:46
 */
public class ProductionLineResultMessage {

    /**
     * status : 400
     * message : 该位置已存在生产线
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

    @Override
    public String toString() {
        return "ProductionLineResultMessage{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
