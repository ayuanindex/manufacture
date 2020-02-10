package com.lenovo.topic11.bean;

import java.util.List;

public class WorkInfoBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":"1","partCapacity":100,"carCapacity":50000,"price":15000}]
     */

    public int status;
    public String message;
    public List<DataBean> data;

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
         * partCapacity : 100
         * carCapacity : 50000
         * price : 15000
         */

        public String id;
        public int partCapacity;
        public int carCapacity;
        public int price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPartCapacity() {
            return partCapacity;
        }

        public void setPartCapacity(int partCapacity) {
            this.partCapacity = partCapacity;
        }

        public int getCarCapacity() {
            return carCapacity;
        }

        public void setCarCapacity(int carCapacity) {
            this.carCapacity = carCapacity;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", partCapacity=" + partCapacity +
                    ", carCapacity=" + carCapacity +
                    ", price=" + price +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WorkInfoBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
