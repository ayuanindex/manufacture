package com.lenovo.topic19;

import java.util.List;

//出入库采用的是同一个Bean进行解析的，解析的是Bean有的属性，
// 所以可以通用一个Bean解析
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
        /**
         * id : 140
         * userFactoryId : 1
         * userLineId : 2513
         * supplyListId : 5
         * num : 2
         * time : 1582782181
         * price : 2700
         * lastUpdateTime : null
         * supplyName : 新星汽车配件
         * materialName : 轿车座椅
         * lineName : null
         */

        private int id;
        private int userFactoryId;
        private int userLineId;
        private int supplyListId;
        private int num;
        private int time;
        private int price;
        private String lastUpdateTime;
        private String supplyName;
        private String materialName;
        private String type;//确定出入库方向
        private String timeStr;
        private String lineName;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
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

        public int getUserLineId() {
            return userLineId;
        }

        public void setUserLineId(int userLineId) {
            this.userLineId = userLineId;
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

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
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

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }
    }
}