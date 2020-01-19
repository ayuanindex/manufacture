package com.lenovo.topic6.bean;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic6.bean
 * @ClassName: AllProductionLineBean
 * @Author: AYuan
 * @CreateDate: 2020/1/19 10:37
 */
public class AllProductionLineBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":2461,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":2,"isAI":0},{"id":2460,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":0,"isAI":0},{"id":2459,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":1,"isAI":0}]
     */

    private int status;
    private String message;
    private List<LineBean> data;

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

    public List<LineBean> getData() {
        return data;
    }

    public void setData(List<LineBean> data) {
        this.data = data;
    }

    public static class LineBean {
        /**
         * id : 2461
         * userWorkId : 1
         * stageId : 25
         * productionLineId : 1
         * type : 0
         * position : 2
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
            return "LineBean{" +
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
        return "AllProductionLineBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
