package com.ayuan.topic2.bean;

public class UpdateAcOnOff {

    /**
     * status : 200
     * message : SUCCESS
     * data : {"id":1,"day":"0","lightSwitch":0,"acOnOff":"2","beam":"245","workshopTemp":"22℃","outTemp":"17℃","power":"110","powerConsume":"0","time":"07:15"}
     */

    public int status;
    public String message;
    public DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * day : 0
         * lightSwitch : 0
         * acOnOff : 2
         * beam : 245
         * workshopTemp : 22℃
         * outTemp : 17℃
         * power : 110
         * powerConsume : 0
         * time : 07:15
         */

        public int id;
        public String day;
        public int lightSwitch;
        public String acOnOff;
        public String beam;
        public String workshopTemp;
        public String outTemp;
        public String power;
        public String powerConsume;
        public String time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getLightSwitch() {
            return lightSwitch;
        }

        public void setLightSwitch(int lightSwitch) {
            this.lightSwitch = lightSwitch;
        }

        public String getAcOnOff() {
            return acOnOff;
        }

        public void setAcOnOff(String acOnOff) {
            this.acOnOff = acOnOff;
        }

        public String getBeam() {
            return beam;
        }

        public void setBeam(String beam) {
            this.beam = beam;
        }

        public String getWorkshopTemp() {
            return workshopTemp;
        }

        public void setWorkshopTemp(String workshopTemp) {
            this.workshopTemp = workshopTemp;
        }

        public String getOutTemp() {
            return outTemp;
        }

        public void setOutTemp(String outTemp) {
            this.outTemp = outTemp;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getPowerConsume() {
            return powerConsume;
        }

        public void setPowerConsume(String powerConsume) {
            this.powerConsume = powerConsume;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", day='" + day + '\'' +
                    ", lightSwitch=" + lightSwitch +
                    ", acOnOff='" + acOnOff + '\'' +
                    ", beam='" + beam + '\'' +
                    ", workshopTemp='" + workshopTemp + '\'' +
                    ", outTemp='" + outTemp + '\'' +
                    ", power='" + power + '\'' +
                    ", powerConsume='" + powerConsume + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpdateAcOnOff{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
