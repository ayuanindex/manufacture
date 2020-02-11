package com.lenovo.topic18;

import java.util.List;


//查询全部维修车辆成品仓库
public class UserRepairCarStoreGetAll {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":3442,"userWorkId":1,"userProductionLineId":2,"carId":5,"num":5},{"id":3441,"userWorkId":1,"userProductionLineId":2,"carId":5,"num":5},{"id":3440,"userWorkId":1,"userProductionLineId":2,"carId":5,"num":5},{"id":3439,"userWorkId":1,"userProductionLineId":1,"carId":5,"num":5},{"id":3438,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3437,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3436,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3435,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3434,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3433,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3432,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3430,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3429,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3428,"userWorkId":1,"userProductionLineId":1,"carId":2,"num":5},{"id":3427,"userWorkId":1,"userProductionLineId":1,"carId":1,"num":1}]
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
         * id : 3442
         * userWorkId : 1
         * userProductionLineId : 2
         * carId : 5
         * num : 5
         */

        private int id;
        private int userWorkId;
        private int userProductionLineId;
        private int carId;
        private int num;

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

        public int getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(int userProductionLineId) {
            this.userProductionLineId = userProductionLineId;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
