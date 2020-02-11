package com.lenovo.topic08.bean;

import java.util.List;

public class LineToPeople {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":3651,"userWorkId":1,"power":100,"peopleId":1,"userProductionLineId":2479,"workPostId":5}]
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
         * id : 3651
         * userWorkId : 1
         * power : 100
         * peopleId : 1
         * userProductionLineId : 2479
         * workPostId : 5
         */

        public int id;
        public int userWorkId;
        public int power;
        public int peopleId;
        public int userProductionLineId;
        public int workPostId;

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

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public int getPeopleId() {
            return peopleId;
        }

        public void setPeopleId(int peopleId) {
            this.peopleId = peopleId;
        }

        public int getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(int userProductionLineId) {
            this.userProductionLineId = userProductionLineId;
        }

        public int getWorkPostId() {
            return workPostId;
        }

        public void setWorkPostId(int workPostId) {
            this.workPostId = workPostId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", power=" + power +
                    ", peopleId=" + peopleId +
                    ", userProductionLineId=" + userProductionLineId +
                    ", workPostId=" + workPostId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LineToPeople{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
