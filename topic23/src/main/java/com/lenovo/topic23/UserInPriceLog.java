package com.lenovo.topic23;

import java.util.List;

/**
 * “查询全部学生金币收入日志”接口 请求的Json数据转化成实体类
 */
public class UserInPriceLog {
    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":725,"userWorkId":1,"price":1000,"endPrice":15500,"time":"2019-12-18 14:55:00","type":5},{"id":724,"userWorkId":1,"price":4500,"endPrice":14500,"time":"2019-12-18 14:45:00","type":5},{"id":723,"userWorkId":1,"price":800,"endPrice":10000,"time":"2019-12-18 14:35:00","type":5},{"id":722,"userWorkId":1,"price":2000,"endPrice":9200,"time":"2019-12-18 14:30:00","type":5}]
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
         * id : 725
         * userWorkId : 1
         * price : 1000
         * endPrice : 15500
         * time : 2019-12-18 14:55:00
         * type : 5
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
