package com.lenovo.topic05.bean;

import java.util.List;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic5.bean
 * @ClassName: AllPerson
 * @CreateDate: 2020/1/18 17:29
 */
public class AllPerson {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":1,"peopleName":"李刚","icon":null,"status":0,"talentMarketId":1,"gold":2000,"hp":100,"content":"汽车工程师"},{"id":2,"peopleName":"丁运生","icon":null,"status":1,"talentMarketId":1,"gold":500,"hp":100,"content":"汽车厂工人"},{"id":3,"peopleName":"方华高","icon":null,"status":2,"talentMarketId":1,"gold":3000,"hp":100,"content":"汽车工厂技术人员"},{"id":30,"peopleName":"朱云贵","icon":null,"status":1,"talentMarketId":1,"gold":800,"hp":100,"content":"汽车厂工人"},{"id":5,"peopleName":"邹辉","icon":null,"status":0,"talentMarketId":1,"gold":1500,"hp":100,"content":"汽车工程师"},{"id":6,"peopleName":"杨文","icon":null,"status":1,"talentMarketId":1,"gold":800,"hp":100,"content":"汽车厂工人"},{"id":7,"peopleName":"朱元元","icon":null,"status":2,"talentMarketId":1,"gold":7000,"hp":100,"content":"汽车工厂技术人员"},{"id":8,"peopleName":"周正发","icon":null,"status":3,"talentMarketId":1,"gold":4000,"hp":100,"content":"汽车质检员"},{"id":9,"peopleName":"张伟","icon":null,"status":0,"talentMarketId":1,"gold":3000,"hp":100,"content":"汽车工程师"},{"id":10,"peopleName":"周丽","icon":null,"status":1,"talentMarketId":1,"gold":600,"hp":100,"content":"汽车厂工人"},{"id":12,"peopleName":"陈天云","icon":null,"status":3,"talentMarketId":1,"gold":1650,"hp":100,"content":"汽车质检员"},{"id":11,"peopleName":"陈敏","icon":null,"status":2,"talentMarketId":1,"gold":1420,"hp":100,"content":"技术人员"},{"id":13,"peopleName":"王百年","icon":null,"status":0,"talentMarketId":1,"gold":1300,"hp":100,"content":"汽车工程师"},{"id":14,"peopleName":"王莉","icon":null,"status":1,"talentMarketId":1,"gold":1700,"hp":100,"content":"汽车厂工人"},{"id":15,"peopleName":"杨保俊","icon":null,"status":2,"talentMarketId":1,"gold":4000,"hp":100,"content":"汽车工厂技术人员"},{"id":16,"peopleName":"张大伟","icon":null,"status":3,"talentMarketId":1,"gold":1200,"hp":100,"content":"汽车质检员"},{"id":17,"peopleName":"徐超","icon":null,"status":0,"talentMarketId":1,"gold":3600,"hp":100,"content":"汽车工程师"},{"id":18,"peopleName":"于少明","icon":null,"status":1,"talentMarketId":1,"gold":1300,"hp":100,"content":"汽车厂工人"},{"id":19,"peopleName":"吴雪平","icon":null,"status":2,"talentMarketId":1,"gold":785,"hp":100,"content":"汽车工厂技术人员"},{"id":22,"peopleName":"崔鹏","icon":null,"status":1,"talentMarketId":1,"gold":3000,"hp":100,"content":"汽车厂工人"},{"id":23,"peopleName":"David","icon":null,"status":2,"talentMarketId":1,"gold":3500,"hp":100,"content":"汽车工厂技术人员"},{"id":24,"peopleName":"张先龙","icon":null,"status":3,"talentMarketId":1,"gold":2300,"hp":100,"content":"汽车质检员"},{"id":25,"peopleName":"邓宁","icon":null,"status":0,"talentMarketId":1,"gold":1500,"hp":100,"content":"汽车工程师"},{"id":26,"peopleName":"钟华国","icon":null,"status":1,"talentMarketId":1,"gold":1600,"hp":100,"content":"汽车厂工人"},{"id":27,"peopleName":"罗梅","icon":null,"status":2,"talentMarketId":1,"gold":1700,"hp":100,"content":"汽车工厂技术人员"},{"id":29,"peopleName":"张锋","icon":null,"status":0,"talentMarketId":1,"gold":800,"hp":100,"content":"汽车工程师"},{"id":28,"peopleName":"王琪","icon":null,"status":3,"talentMarketId":1,"gold":1300,"hp":100,"content":"汽车质检员"},{"id":32,"peopleName":"李芳","icon":null,"status":3,"talentMarketId":1,"gold":1600,"hp":100,"content":"汽车质检员"},{"id":31,"peopleName":"李冰","icon":null,"status":2,"talentMarketId":1,"gold":1700,"hp":100,"content":"技术"},{"id":4,"peopleName":"省均","icon":null,"status":3,"talentMarketId":1,"gold":2600,"hp":100,"content":"汽车质检员"},{"id":21,"peopleName":"张旭","icon":null,"status":0,"talentMarketId":1,"gold":3000,"hp":100,"content":"汽车工程师"},{"id":20,"peopleName":"杨庆春","icon":null,"status":3,"talentMarketId":1,"gold":3650,"hp":100,"content":"汽车质检员"}]
     */

    private int status;
    private String message;
    private List<PersonBean> data;

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

    public List<PersonBean> getData() {
        return data;
    }

    public void setData(List<PersonBean> data) {
        this.data = data;
    }

    public static class PersonBean {

        /**
         * id : 1
         * peopleName : 李刚
         * icon : null
         * status : 0
         * talentMarketId : 1
         * gold : 2000
         * hp : 100
         * content : 汽车工程师
         */

        private int id;
        private String peopleName;
        private Object icon;
        private int status;
        private int talentMarketId;
        private int gold;
        private int hp;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPeopleName() {
            return peopleName;
        }

        public void setPeopleName(String peopleName) {
            this.peopleName = peopleName;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTalentMarketId() {
            return talentMarketId;
        }

        public void setTalentMarketId(int talentMarketId) {
            this.talentMarketId = talentMarketId;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public int getHp() {
            return hp;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "PersonBean{" +
                    "id=" + id +
                    ", peopleName='" + peopleName + '\'' +
                    ", icon=" + icon +
                    ", status=" + status +
                    ", talentMarketId=" + talentMarketId +
                    ", gold=" + gold +
                    ", hp=" + hp +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllPerson{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
