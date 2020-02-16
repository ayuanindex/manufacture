package com.lenovo.topic09.bean;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic09.bean
 * @ClassName: UserPeopleBean
 * @CreateDate: 2020/2/12 17:04
 */
public class UserPeopleBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":4079,"userWorkId":1,"power":100,"peopleId":4,"userProductionLineId":2507,"workPostId":12},{"id":4078,"userWorkId":1,"power":100,"peopleId":32,"userProductionLineId":2507,"workPostId":12},{"id":4077,"userWorkId":1,"power":100,"peopleId":29,"userProductionLineId":2507,"workPostId":9},{"id":4076,"userWorkId":1,"power":100,"peopleId":24,"userProductionLineId":2507,"workPostId":12},{"id":4075,"userWorkId":1,"power":100,"peopleId":22,"userProductionLineId":2507,"workPostId":10},{"id":4074,"userWorkId":1,"power":100,"peopleId":16,"userProductionLineId":2507,"workPostId":12},{"id":4073,"userWorkId":1,"power":100,"peopleId":15,"userProductionLineId":2507,"workPostId":11},{"id":4072,"userWorkId":1,"power":100,"peopleId":13,"userProductionLineId":2507,"workPostId":9},{"id":4071,"userWorkId":1,"power":100,"peopleId":10,"userProductionLineId":2507,"workPostId":10},{"id":4070,"userWorkId":1,"power":100,"peopleId":8,"userProductionLineId":2507,"workPostId":12},{"id":4069,"userWorkId":1,"power":100,"peopleId":30,"userProductionLineId":2507,"workPostId":10},{"id":4068,"userWorkId":1,"power":100,"peopleId":5,"userProductionLineId":2507,"workPostId":9},{"id":4067,"userWorkId":1,"power":100,"peopleId":1,"userProductionLineId":2507,"workPostId":9}]
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
         * id : 4079
         * userWorkId : 1
         * power : 100
         * peopleId : 4
         * userProductionLineId : 2507
         * workPostId : 12
         */

        private int id;
        private int userWorkId;
        private int power;
        private int peopleId;
        private int userProductionLineId;
        private int workPostId;

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
        return "UserPeopleBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}