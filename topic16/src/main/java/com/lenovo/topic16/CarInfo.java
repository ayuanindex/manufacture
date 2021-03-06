package com.lenovo.topic16;

import java.util.List;

public class CarInfo {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":"1","gold":2500,"area":6,"repairGold":500}]
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
         * id : 1
         * gold : 2500
         * area : 6
         * repairGold : 500
         */

        private String id;
        private int gold;
        private int area;
        private int repairGold;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public int getRepairGold() {
            return repairGold;
        }

        public void setRepairGold(int repairGold) {
            this.repairGold = repairGold;
        }
    }
}
