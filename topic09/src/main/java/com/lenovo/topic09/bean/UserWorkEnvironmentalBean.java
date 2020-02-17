package com.lenovo.topic09.bean;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic09.bean
 * @ClassName: UserWorkEnvironmentalBean
 * @CreateDate: 2020/2/17 12:46
 */
public class UserWorkEnvironmentalBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":"1","userWorkId":1,"day":"1","lightSwitch":1,"acOnOff":1,"beam":1,"workshopTemp":"1","outTemp":"1","power":1,"powerConsume":"1","time":"12:22"}]
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
         * userWorkId : 1
         * day : 1
         * lightSwitch : 1
         * acOnOff : 1
         * beam : 1
         * workshopTemp : 1
         * outTemp : 1
         * power : 1
         * powerConsume : 1
         * time : 12:22
         */

        private String id;
        private int userWorkId;
        private String day;
        private int lightSwitch;
        private int acOnOff;
        private int beam;
        private String workshopTemp;
        private String outTemp;
        private int power;
        private String powerConsume;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(int userWorkId) {
            this.userWorkId = userWorkId;
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

        public int getAcOnOff() {
            return acOnOff;
        }

        public void setAcOnOff(int acOnOff) {
            this.acOnOff = acOnOff;
        }

        public int getBeam() {
            return beam;
        }

        public void setBeam(int beam) {
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

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
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
                    "id='" + id + '\'' +
                    ", userWorkId=" + userWorkId +
                    ", day='" + day + '\'' +
                    ", lightSwitch=" + lightSwitch +
                    ", acOnOff=" + acOnOff +
                    ", beam=" + beam +
                    ", workshopTemp='" + workshopTemp + '\'' +
                    ", outTemp='" + outTemp + '\'' +
                    ", power=" + power +
                    ", powerConsume='" + powerConsume + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserWorkEnvironmentalBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
