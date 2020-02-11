package com.lenovo.topic12.bean;

import java.util.List;

public class ProductionLineBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":2497,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0}]
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
         * id : 2497
         * userWorkId : 1
         * stageId : 5
         * productionLineId : 2
         * type : 0
         * position : 0
         * isAI : 0
         */

        public int id;
        public int userWorkId;
        public int stageId;
        public int productionLineId;
        public int type;
        public int position;
        public int isAI;
        public String productionLineName = "";

        public DataBean(int position) {
            this.position = position;
        }

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

        public int getStageId() {
            return stageId;
        }

        public void setStageId(int stageId) {
            this.stageId = stageId;
        }

        public int getProductionLineId() {
            return productionLineId;
        }

        public void setProductionLineId(int productionLineId) {
            this.productionLineId = productionLineId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getIsAI() {
            return isAI;
        }

        public void setIsAI(int isAI) {
            this.isAI = isAI;
        }

        public String getProductionLineName() {
            return productionLineName;
        }

        public void setProductionLineName(String productionLineName) {
            this.productionLineName = productionLineName;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", stageId=" + stageId +
                    ", productionLineId=" + productionLineId +
                    ", type=" + type +
                    ", position=" + position +
                    ", isAI=" + isAI +
                    ", productionLineName='" + productionLineName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductionLineBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
