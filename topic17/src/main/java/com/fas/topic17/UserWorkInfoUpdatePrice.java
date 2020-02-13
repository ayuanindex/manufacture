package com.fas.topic17;

/**
 * 修改学生工厂信息金币
 */
public class UserWorkInfoUpdatePrice {


    /**
     * status : 200
     * message : SUCCESS
     * data : {"id":1,"partCapacity":100,"carCapacity":50000,"price":"2000"}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * partCapacity : 100
         * carCapacity : 50000
         * price : 2000
         */

        private int id;
        private int partCapacity;
        private int carCapacity;
        private String price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
