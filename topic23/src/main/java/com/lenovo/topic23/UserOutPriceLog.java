package com.lenovo.topic23;

import java.util.List;

/**
 * "查询全部学生金币支出日志“ 接口请求数据json转换为实体类
 */
public class UserOutPriceLog {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":729,"userWorkId":1,"price":500,"endPrice":10000,"time":"2019-12-18 16:30:00","type":2},{"id":728,"userWorkId":1,"price":500,"endPrice":10500,"time":"2019-12-18 16:15:00","type":2},{"id":727,"userWorkId":1,"price":3000,"endPrice":11000,"time":"2019-12-18 15:55:00","type":1},{"id":726,"userWorkId":1,"price":1500,"endPrice":14000,"time":"2019-12-18 15:05:00","type":0},{"id":721,"userWorkId":1,"price":1200,"endPrice":7200,"time":"2019-12-18 14:15:00","type":0},{"id":720,"userWorkId":1,"price":1600,"endPrice":8400,"time":"2019-12-18 14:10:00","type":0},{"id":719,"userWorkId":1,"price":15000,"endPrice":10000,"time":"2019-12-18 14:00:00","type":0},{"id":718,"userWorkId":1,"price":3000,"endPrice":11500,"time":"2019-12-18 13:55:00","type":0},{"id":717,"userWorkId":1,"price":500,"endPrice":14500,"time":"2019-12-18 13:45:00","type":4},{"id":716,"userWorkId":1,"price":1000,"endPrice":15000,"time":"2019-12-18 13:40:00","type":4},{"id":715,"userWorkId":1,"price":2000,"endPrice":16000,"time":"2019-12-18 13:30:00","type":4},{"id":714,"userWorkId":1,"price":1500,"endPrice":18000,"time":"2019-12-18 13:15:00","type":4},{"id":713,"userWorkId":1,"price":500,"endPrice":19500,"time":"2019-12-18 13:20:00","type":4},{"id":712,"userWorkId":1,"price":1000,"endPrice":20000,"time":"2019-12-18 13:10:00","type":4},{"id":711,"userWorkId":1,"price":1500,"endPrice":21000,"time":"2019-12-18 12:55:00","type":4},{"id":710,"userWorkId":1,"price":2000,"endPrice":22500,"time":"2019-12-18 12:35:00","type":0},{"id":709,"userWorkId":1,"price":3000,"endPrice":24500,"time":"2019-12-18 12:20:00","type":0},{"id":708,"userWorkId":1,"price":500,"endPrice":27500,"time":"2019-12-18 12:00:00","type":0},{"id":707,"userWorkId":1,"price":5000,"endPrice":28000,"time":"2019-12-18 11:55:00","type":3},{"id":706,"userWorkId":1,"price":1500,"endPrice":33000,"time":"2019-12-18 11:45:00","type":3},{"id":705,"userWorkId":1,"price":500,"endPrice":34500,"time":"2019-12-18 11:42:00","type":3},{"id":704,"userWorkId":1,"price":1500,"endPrice":35000,"time":"2019-12-18 11:40:00","type":2},{"id":703,"userWorkId":1,"price":1500,"endPrice":37500,"time":"2019-12-18 11:30:00","type":2},{"id":702,"userWorkId":1,"price":3000,"endPrice":39000,"time":"2019-12-18 11:12:00","type":1},{"id":701,"userWorkId":1,"price":3000,"endPrice":42000,"time":"2019-12-18 10:10:00","type":0}]
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
         * id : 729
         * userWorkId : 1
         * price : 500
         * endPrice : 10000
         * time : 2019-12-18 16:30:00
         * type : 2
         */

        private int id;
        private int userWorkId;
        private int price;
        private int endPrice;
        private String time;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(int userWorkId) {
            this.userWorkId = userWorkId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getEndPrice() {
            return endPrice;
        }

        public void setEndPrice(int endPrice) {
            this.endPrice = endPrice;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
