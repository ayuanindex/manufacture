package com.lenovo.topic14.bean;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic14.bean
 * @ClassName: UserPlStepBean
 * @CreateDate: 2020/2/12 14:42
 */
public class UserPlStepBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":21628,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":-1},{"id":21627,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":64},{"id":21626,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":63},{"id":21625,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":62},{"id":21624,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":61},{"id":21623,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":60},{"id":21622,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":59},{"id":21621,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":58},{"id":21620,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":57},{"id":21619,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":56},{"id":21618,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":55},{"id":21617,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":54},{"id":21616,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":53},{"id":21615,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":52},{"id":21614,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":51},{"id":21613,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":50},{"id":21612,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":49},{"id":21611,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":48},{"id":21610,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":47},{"id":21609,"userWorkId":1,"userProductionLineId":2507,"nextUserPlStepId":46}]
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
         * id : 21628
         * userWorkId : 1
         * userProductionLineId : 2507
         * nextUserPlStepId : -1
         */

        private int id;
        private int userWorkId;
        private int userProductionLineId;
        private int nextUserPlStepId;

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

        public int getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(int userProductionLineId) {
            this.userProductionLineId = userProductionLineId;
        }

        public int getNextUserPlStepId() {
            return nextUserPlStepId;
        }

        public void setNextUserPlStepId(int nextUserPlStepId) {
            this.nextUserPlStepId = nextUserPlStepId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", userProductionLineId=" + userProductionLineId +
                    ", nextUserPlStepId=" + nextUserPlStepId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserPlStepBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}