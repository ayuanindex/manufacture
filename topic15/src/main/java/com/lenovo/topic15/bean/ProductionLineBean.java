package com.lenovo.topic15.bean;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic15.bean
 * @ClassName: ProductionLine
 * @CreateDate: 2020/2/16 13:28
 */
public class ProductionLineBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":2507,"userWorkId":1,"stageId":45,"productionLineId":3,"type":0,"position":3,"isAI":0}]
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
         * id : 2507
         * userWorkId : 1
         * stageId : 45
         * productionLineId : 3
         * type : 0
         * position : 3
         * isAI : 0
         */

        private int id;
        private int userWorkId;
        private int stageId;
        private int productionLineId;
        private int type;
        private int position;
        private int isAI;

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
