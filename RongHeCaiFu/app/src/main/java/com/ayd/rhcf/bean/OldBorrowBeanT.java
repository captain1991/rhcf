package com.ayd.rhcf.bean;

import java.io.Serializable;

/**
 * Created by yxd on 2016/7/28.
 * 这个应该没用
 */
public class OldBorrowBeanT implements Serializable {

    /**
     * user_info : {"company_position":"其他","is_house":"无车","realname":false}
     * borrow_upfiles :
     * ronghe_third : null
     * view_type : 0
     * user_id : 344
     * open_tender : 0
     * borrow_pawn_description : 这是抵押物
     * tender_time : 0
     */

    private DataBean data;
    /**
     * data : {"user_info":{"company_position":"其他","is_house":"无车","realname":false},"borrow_upfiles":"","ronghe_third":null,"view_type":"0","user_id":"344","open_tender":"0","borrow_pawn_description":"这是抵押物","tender_time":"0"}
     * msg : 获取列表成功
     * code : 0
     */

    private String msg;
    private String code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * company_position : 其他
         * is_house : 无车
         * realname : false
         */

        private UserInfoBean user_info;
        private String borrow_upfiles;
        private Object ronghe_third;
        private String view_type;
        private String user_id;
        private String open_tender;
        private String borrow_pawn_description;
        private String tender_time;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public String getBorrow_upfiles() {
            return borrow_upfiles;
        }

        public void setBorrow_upfiles(String borrow_upfiles) {
            this.borrow_upfiles = borrow_upfiles;
        }

        public Object getRonghe_third() {
            return ronghe_third;
        }

        public void setRonghe_third(Object ronghe_third) {
            this.ronghe_third = ronghe_third;
        }

        public String getView_type() {
            return view_type;
        }

        public void setView_type(String view_type) {
            this.view_type = view_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOpen_tender() {
            return open_tender;
        }

        public void setOpen_tender(String open_tender) {
            this.open_tender = open_tender;
        }

        public String getBorrow_pawn_description() {
            return borrow_pawn_description;
        }

        public void setBorrow_pawn_description(String borrow_pawn_description) {
            this.borrow_pawn_description = borrow_pawn_description;
        }

        public String getTender_time() {
            return tender_time;
        }

        public void setTender_time(String tender_time) {
            this.tender_time = tender_time;
        }

        public static class UserInfoBean {
            private String company_position;
            private String is_house;
            private boolean realname;

            public String getCompany_position() {
                return company_position;
            }

            public void setCompany_position(String company_position) {
                this.company_position = company_position;
            }

            public String getIs_house() {
                return is_house;
            }

            public void setIs_house(String is_house) {
                this.is_house = is_house;
            }

            public boolean isRealname() {
                return realname;
            }

            public void setRealname(boolean realname) {
                this.realname = realname;
            }
        }
    }
}
