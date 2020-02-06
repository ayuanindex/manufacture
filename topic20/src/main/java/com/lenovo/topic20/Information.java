package com.lenovo.topic20;

import java.util.List;

public class Information {

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
         * id : 5
         * informationName : 买吉利认准这个工厂！吉利CMA工厂参观
         * time : 1571880600
         * words : 汽车作为世界上技术含量、制造要求最高的工业产品之一，汽车厂商唯独掌握核心技术才能长久立足。在现阶段，模块化架构已经成为全球车企最新追逐的技术方向之一，其高度灵活的可扩展性和规模优势已经成为汽车企业实现新跨越的技术支撑。
         * <p>
         * 　　作为中国品牌汽车的领军企业，吉利依托沃尔沃的技术优势，两者在2016年合作开发了全新的CMA架构，领克品牌旗下车型以及沃尔沃XC40正是基于此架构打造，从市场反馈来看，CMA架构体系下车型获得了不错的认可。如今，原本服务于沃尔沃和领克品牌的CMA架构实现了进一步下放，吉利品牌也基于此架构推出了全新车型——吉利星越
         * video : null
         * icon : null
         * status : 0
         */

        private int id;
        private String informationName;
        private Long time;
        private String words;
        private String video;
        private String icon;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInformationName() {
            return informationName;
        }

        public void setInformationName(String informationName) {
            this.informationName = informationName;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public Object getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
