package com.lenovo.topic18;

import java.util.List;

public class CarType {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":1,"carName":"轿车汽车","content":"轿车汽车标准型"},{"id":2,"carName":"MPV汽车","content":"MPV汽车标准型"},{"id":3,"carName":"SUV汽车","content":"SUV汽车标准型"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * carName : 轿车汽车
         * content : 轿车汽车标准型
         */

        private int id;
        private String carName;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
