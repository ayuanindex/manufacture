package com.lenovo.topic07.bean;

import java.util.List;
import java.util.Objects;

public class AllProductioinLine {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":2466,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":0,"isAI":0},{"id":2465,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":0,"isAI":0},{"id":2464,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":0,"isAI":0},{"id":2463,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2467,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2468,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2469,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2470,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2471,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2472,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2473,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2474,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2475,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":0,"isAI":0},{"id":2476,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":1,"isAI":0},{"id":2477,"userWorkId":1,"stageId":45,"productionLineId":3,"type":0,"position":2,"isAI":0},{"id":2478,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":3,"isAI":0}]
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
         * id : 2466
         * userWorkId : 1
         * stageId : 25
         * productionLineId : 1
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DataBean dataBean = (DataBean) o;
            return position == dataBean.position;
        }

        @Override
        public int hashCode() {
            return Objects.hash(position);
        }
    }

    @Override
    public String toString() {
        return "AllProductioinLine{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
