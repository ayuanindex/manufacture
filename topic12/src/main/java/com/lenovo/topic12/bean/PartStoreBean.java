package com.lenovo.topic12.bean;

import java.util.List;

public class PartStoreBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":6012,"userWorkId":1,"partId":37,"userProductionLineId":2503,"num":1},{"id":6011,"userWorkId":1,"partId":25,"userProductionLineId":2503,"num":1},{"id":6010,"userWorkId":1,"partId":22,"userProductionLineId":2503,"num":1},{"id":6009,"userWorkId":1,"partId":21,"userProductionLineId":2503,"num":4},{"id":6008,"userWorkId":1,"partId":24,"userProductionLineId":2503,"num":4},{"id":6007,"userWorkId":1,"partId":26,"userProductionLineId":2503,"num":2}]
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
         * id : 6012
         * userWorkId : 1
         * partId : 37
         * userProductionLineId : 2503
         * num : 1
         */

        public int id;
        public int userWorkId;
        public int partId;
        public int userProductionLineId;
        public int num;
        public String name;
        public String productionLineName = "";


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

        public int getPartId() {
            return partId;
        }

        public void setPartId(int partId) {
            this.partId = partId;
        }

        public int getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(int userProductionLineId) {
            this.userProductionLineId = userProductionLineId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProductionLineName() {
            return productionLineName;
        }

        public void setProductionLineName(String productionLineName) {
            this.productionLineName = productionLineName;
        }

        public void setProductionLineName(int userProductionLineId) {
            switch (userProductionLineId) {
                case 1:
                    setProductionLineName("轿车车型生产线");
                    break;
                case 2:
                    setProductionLineName("MPV车型生产线");
                    break;
                case 3:
                    setProductionLineName("SUV车型生产线");
                    break;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", partId=" + partId +
                    ", userProductionLineId=" + userProductionLineId +
                    ", num=" + num +
                    ", name='" + name + '\'' +
                    ", productionLineName='" + productionLineName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PartStoreBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
