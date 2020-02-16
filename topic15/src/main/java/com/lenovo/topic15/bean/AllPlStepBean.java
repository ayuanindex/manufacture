package com.lenovo.topic15.bean;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic15.bean
 * @ClassName: AllPlStepBean
 * @CreateDate: 2020/2/16 15:33
 */
public class AllPlStepBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":21668,"plStepName":"MPV车型生产环节20","stageId":24,"power":100,"costTime":"0","step":20,"consume":30},{"id":21667,"plStepName":"MPV车型生产环节19","stageId":23,"power":100,"costTime":"0","step":19,"consume":30},{"id":21666,"plStepName":"MPV车型生产环节18","stageId":22,"power":100,"costTime":"0","step":18,"consume":30},{"id":21665,"plStepName":"MPV车型生产环节17","stageId":21,"power":100,"costTime":"0","step":17,"consume":30},{"id":21664,"plStepName":"MPV车型生产环节16","stageId":20,"power":100,"costTime":"0","step":16,"consume":30},{"id":21663,"plStepName":"MPV车型生产环节15","stageId":19,"power":100,"costTime":"0","step":15,"consume":30},{"id":21662,"plStepName":"MPV车型生产环节14","stageId":18,"power":100,"costTime":"0","step":14,"consume":30},{"id":21661,"plStepName":"MPV车型生产环节13","stageId":17,"power":100,"costTime":"0","step":13,"consume":30},{"id":21660,"plStepName":"MPV车型生产环节12","stageId":16,"power":100,"costTime":"0","step":12,"consume":30},{"id":21659,"plStepName":"MPV车型生产环节11","stageId":15,"power":100,"costTime":"0","step":11,"consume":30},{"id":21658,"plStepName":"MPV车型生产环节10","stageId":14,"power":100,"costTime":"0","step":10,"consume":30},{"id":21657,"plStepName":"MPV车型生产环节9","stageId":13,"power":100,"costTime":"0","step":9,"consume":30},{"id":21656,"plStepName":"MPV车型生产环节8","stageId":11,"power":100,"costTime":"0","step":8,"consume":30},{"id":21655,"plStepName":"MPV车型生产环节7","stageId":12,"power":100,"costTime":"0","step":7,"consume":30},{"id":21654,"plStepName":"MPV车型生产环节6","stageId":10,"power":100,"costTime":"0","step":6,"consume":30},{"id":21653,"plStepName":"MPV车型生产环节5","stageId":9,"power":100,"costTime":"0","step":5,"consume":30},{"id":21652,"plStepName":"MPV车型生产环节4","stageId":8,"power":100,"costTime":"0","step":4,"consume":30},{"id":21651,"plStepName":"MPV车型生产环节3","stageId":7,"power":100,"costTime":"0","step":3,"consume":30},{"id":21650,"plStepName":"MPV车型生产环节2","stageId":6,"power":100,"costTime":"0","step":2,"consume":30},{"id":21649,"plStepName":"MPV车型生产环节1","stageId":5,"power":100,"costTime":"0","step":1,"consume":30},{"id":21648,"plStepName":"轿车车型生产环节20","stageId":44,"power":100,"costTime":"0","step":20,"consume":30},{"id":21647,"plStepName":"轿车车型生产环节19","stageId":43,"power":100,"costTime":"0","step":19,"consume":30},{"id":21646,"plStepName":"轿车车型生产环节18","stageId":42,"power":100,"costTime":"0","step":18,"consume":30},{"id":21645,"plStepName":"轿车车型生产环节17","stageId":41,"power":100,"costTime":"0","step":17,"consume":30},{"id":21644,"plStepName":"轿车车型生产环节16","stageId":40,"power":100,"costTime":"0","step":16,"consume":30},{"id":21643,"plStepName":"轿车车型生产环节15","stageId":39,"power":100,"costTime":"0","step":15,"consume":30},{"id":21642,"plStepName":"轿车车型生产环节14","stageId":38,"power":100,"costTime":"0","step":14,"consume":30},{"id":21641,"plStepName":"轿车车型生产环节13","stageId":37,"power":100,"costTime":"0","step":13,"consume":30},{"id":21640,"plStepName":"轿车车型生产环节12","stageId":36,"power":100,"costTime":"0","step":12,"consume":30},{"id":21639,"plStepName":"轿车车型生产环节11","stageId":35,"power":100,"costTime":"0","step":11,"consume":30},{"id":21638,"plStepName":"轿车车型生产环节10","stageId":34,"power":100,"costTime":"0","step":10,"consume":30},{"id":21637,"plStepName":"轿车车型生产环节9","stageId":33,"power":100,"costTime":"0","step":9,"consume":30},{"id":21636,"plStepName":"轿车车型生产环节8","stageId":31,"power":100,"costTime":"0","step":8,"consume":30},{"id":21635,"plStepName":"轿车车型生产环节7","stageId":32,"power":100,"costTime":"0","step":7,"consume":30},{"id":21634,"plStepName":"轿车车型生产环节6","stageId":30,"power":100,"costTime":"0","step":6,"consume":30},{"id":21633,"plStepName":"轿车车型生产环节5","stageId":29,"power":100,"costTime":"0","step":5,"consume":30},{"id":21632,"plStepName":"轿车车型生产环节4","stageId":28,"power":100,"costTime":"0","step":4,"consume":30},{"id":21631,"plStepName":"轿车车型生产环节3","stageId":27,"power":100,"costTime":"0","step":3,"consume":30},{"id":21630,"plStepName":"轿车车型生产环节2","stageId":26,"power":100,"costTime":"0","step":2,"consume":30},{"id":21629,"plStepName":"轿车车型生产环节1","stageId":25,"power":100,"costTime":"0","step":1,"consume":30},{"id":21628,"plStepName":"SUV车型生产环节20","stageId":64,"power":100,"costTime":"0","step":20,"consume":30},{"id":21627,"plStepName":"SUV车型生产环节19","stageId":63,"power":100,"costTime":"0","step":19,"consume":30},{"id":21626,"plStepName":"SUV车型生产环节18","stageId":62,"power":100,"costTime":"0","step":18,"consume":30},{"id":21625,"plStepName":"SUV车型生产环节17","stageId":61,"power":100,"costTime":"0","step":17,"consume":30},{"id":21624,"plStepName":"SUV车型生产环节16","stageId":60,"power":100,"costTime":"0","step":16,"consume":30},{"id":21623,"plStepName":"SUV车型生产环节15","stageId":59,"power":100,"costTime":"0","step":15,"consume":30},{"id":21622,"plStepName":"SUV车型生产环节14","stageId":58,"power":100,"costTime":"0","step":14,"consume":30},{"id":21621,"plStepName":"SUV车型生产环节13","stageId":57,"power":100,"costTime":"0","step":13,"consume":30},{"id":21620,"plStepName":"SUV车型生产环节12","stageId":56,"power":100,"costTime":"0","step":12,"consume":30},{"id":21619,"plStepName":"SUV车型生产环节11","stageId":55,"power":100,"costTime":"0","step":11,"consume":30},{"id":21618,"plStepName":"SUV车型生产环节10","stageId":54,"power":100,"costTime":"0","step":10,"consume":30},{"id":21617,"plStepName":"SUV车型生产环节9","stageId":53,"power":100,"costTime":"0","step":9,"consume":30},{"id":21616,"plStepName":"SUV车型生产环节8","stageId":51,"power":100,"costTime":"0","step":8,"consume":30},{"id":21615,"plStepName":"SUV车型生产环节7","stageId":52,"power":100,"costTime":"0","step":7,"consume":30},{"id":21614,"plStepName":"SUV车型生产环节6","stageId":50,"power":100,"costTime":"0","step":6,"consume":30},{"id":21613,"plStepName":"SUV车型生产环节5","stageId":49,"power":100,"costTime":"0","step":5,"consume":30},{"id":21612,"plStepName":"SUV车型生产环节4","stageId":48,"power":100,"costTime":"0","step":4,"consume":30},{"id":21611,"plStepName":"SUV车型生产环节3","stageId":47,"power":100,"costTime":"0","step":3,"consume":30},{"id":21610,"plStepName":"SUV车型生产环节2","stageId":46,"power":100,"costTime":"0","step":2,"consume":30},{"id":21609,"plStepName":"SUV车型生产环节1","stageId":45,"power":100,"costTime":"0","step":1,"consume":30}]
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
         * id : 21668
         * plStepName : MPV车型生产环节20
         * stageId : 24
         * power : 100
         * costTime : 0
         * step : 20
         * consume : 30
         */

        private int id;
        private String plStepName;
        private int stageId;
        private int power;
        private String costTime;
        private int step;
        private int consume;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlStepName() {
            return plStepName;
        }

        public void setPlStepName(String plStepName) {
            this.plStepName = plStepName;
        }

        public int getStageId() {
            return stageId;
        }

        public void setStageId(int stageId) {
            this.stageId = stageId;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public String getCostTime() {
            return costTime;
        }

        public void setCostTime(String costTime) {
            this.costTime = costTime;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public int getConsume() {
            return consume;
        }

        public void setConsume(int consume) {
            this.consume = consume;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", plStepName='" + plStepName + '\'' +
                    ", stageId=" + stageId +
                    ", power=" + power +
                    ", costTime='" + costTime + '\'' +
                    ", step=" + step +
                    ", consume=" + consume +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllPlStepBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
