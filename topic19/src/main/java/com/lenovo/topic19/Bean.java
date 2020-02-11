package com.lenovo.topic19;

import java.util.List;

public class Bean {

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

        private int id;
        private int userFactoryId;
        private int supplyListId;
        private int num;
        private long time;
        private int price;
        private long lastUpdateTime;
        private String supplyName;
        private String materialName;
        private String type;//出入库方向
        private String timeStr;

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserFactoryId() {
            return userFactoryId;
        }

        public void setUserFactoryId(int userFactoryId) {
            this.userFactoryId = userFactoryId;
        }

        public int getSupplyListId() {
            return supplyListId;
        }

        public void setSupplyListId(int supplyListId) {
            this.supplyListId = supplyListId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getSupplyName() {
            return supplyName;
        }

        public void setSupplyName(String supplyName) {
            this.supplyName = supplyName;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }
    }
}
