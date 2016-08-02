package com.ayd.rhcf.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yxd on 2016/7/19.
 */
public class BannarBean implements Serializable {


    /**
     * code : 0
     * msg : 主页信息
     * data : {"activ_list":[{"url":"/mobliewdqw/index.html","name":"网贷权威","url_app":"/mobliewdqw/index.html","end_time":"1536767999","scroll_pic_url":"/dyupfiles/images/2015-09/18/552_admin_upload_1442562541127.jpg"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * url : /mobliewdqw/index.html
         * name : 网贷权威
         * url_app : /mobliewdqw/index.html
         * end_time : 1536767999
         * scroll_pic_url : /dyupfiles/images/2015-09/18/552_admin_upload_1442562541127.jpg
         */

        private List<ActivListBean> activ_list;

        public List<ActivListBean> getActiv_list() {
            return activ_list;
        }

        public void setActiv_list(List<ActivListBean> activ_list) {
            this.activ_list = activ_list;
        }

        public static class ActivListBean {
            private String url;
            private String name;
            private String url_app;
            private String end_time;
            private String scroll_pic_url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl_app() {
                return url_app;
            }

            public void setUrl_app(String url_app) {
                this.url_app = url_app;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getScroll_pic_url() {
                return scroll_pic_url;
            }

            public void setScroll_pic_url(String scroll_pic_url) {
                this.scroll_pic_url = scroll_pic_url;
            }
        }
    }
}
