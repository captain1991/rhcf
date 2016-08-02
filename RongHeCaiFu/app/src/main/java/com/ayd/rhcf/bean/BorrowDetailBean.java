package com.ayd.rhcf.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yxd on 2016/7/20.
 */
public class BorrowDetailBean implements Serializable {

    /**
     * code : 0
     * msg : 获取列表成功
     * data : {"user_id":"344","borrow_pawn_description":"这是抵押物","open_tender":"","borrow_upfiles":"65020,65021","tender_time":"1465488000","view_type":"1","upfiles_pic":["/dyupfiles/images/2016-04/08/0_admin_upload_1460108949892.jpg","/dyupfiles/images/2016-04/08/0_admin_upload_1460108966170.jpg"],"userinfo":{"realname":"债转真实姓名：","work_city":"债转工作情况：","is_house":"债转资产介绍：","is_car":"债转资金用途：","company_position":"债转信息介绍：","work_year":"200000.00","company_type":{"sfz":"身份证","hkb":"户口本","jhz":"结婚证","gzzm":"工作证明","srzm":"收入证明","gtz":"国土证","fcz":"房产证","fwsd":"房屋实地认证","zxbg":"征信报告","qsdc":"亲属调查","xsz":"行驶证","cldj":"车辆登记证","cldjfp":"车辆登记发票","cljq":"车辆交强险","clsy":"车辆商业保险","clpg":"车辆评估认证","yhls":"银行流水"}}}
     */

    private String code;
    private String msg;
    /**
     * user_id : 344
     * borrow_pawn_description : 这是抵押物
     * open_tender :
     * borrow_upfiles : 65020,65021
     * tender_time : 1465488000
     * view_type : 1
     * upfiles_pic : ["/dyupfiles/images/2016-04/08/0_admin_upload_1460108949892.jpg","/dyupfiles/images/2016-04/08/0_admin_upload_1460108966170.jpg"]
     * userinfo : {"realname":"债转真实姓名：","work_city":"债转工作情况：","is_house":"债转资产介绍：","is_car":"债转资金用途：","company_position":"债转信息介绍：","work_year":"200000.00","company_type":{"sfz":"身份证","hkb":"户口本","jhz":"结婚证","gzzm":"工作证明","srzm":"收入证明","gtz":"国土证","fcz":"房产证","fwsd":"房屋实地认证","zxbg":"征信报告","qsdc":"亲属调查","xsz":"行驶证","cldj":"车辆登记证","cldjfp":"车辆登记发票","cljq":"车辆交强险","clsy":"车辆商业保险","clpg":"车辆评估认证","yhls":"银行流水"}}
     */

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        private String user_id;
        private String borrow_pawn_description;
//        private String open_tender;
        private String borrow_upfiles;
        private String tender_time;
        private String view_type;
        /**
         * realname : 债转真实姓名：
         * work_city : 债转工作情况：
         * is_house : 债转资产介绍：
         * is_car : 债转资金用途：
         * company_position : 债转信息介绍：
         * work_year : 200000.00
         * company_type : {"sfz":"身份证","hkb":"户口本","jhz":"结婚证","gzzm":"工作证明","srzm":"收入证明","gtz":"国土证","fcz":"房产证","fwsd":"房屋实地认证","zxbg":"征信报告","qsdc":"亲属调查","xsz":"行驶证","cldj":"车辆登记证","cldjfp":"车辆登记发票","cljq":"车辆交强险","clsy":"车辆商业保险","clpg":"车辆评估认证","yhls":"银行流水"}
         */

        private UserinfoBean userinfo;
        private List<String> upfiles_pic;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBorrow_pawn_description() {
            return borrow_pawn_description;
        }

        public void setBorrow_pawn_description(String borrow_pawn_description) {
            this.borrow_pawn_description = borrow_pawn_description;
        }

//        public String getOpen_tender() {
//            return open_tender;
//        }
//
//        public void setOpen_tender(String open_tender) {
//            this.open_tender = open_tender;
//        }

        public String getBorrow_upfiles() {
            return borrow_upfiles;
        }

        public void setBorrow_upfiles(String borrow_upfiles) {
            this.borrow_upfiles = borrow_upfiles;
        }

        public String getTender_time() {
            return tender_time;
        }

        public void setTender_time(String tender_time) {
            this.tender_time = tender_time;
        }

        public String getView_type() {
            return view_type;
        }

        public void setView_type(String view_type) {
            this.view_type = view_type;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public List<String> getUpfiles_pic() {
            return upfiles_pic;
        }

        public void setUpfiles_pic(List<String> upfiles_pic) {
            this.upfiles_pic = upfiles_pic;
        }

        public static class UserinfoBean {
            private String realname;
            private String work_city;
            private String is_house;
            private String is_car;
            private String company_position;
            private String work_year;
            /**
             * sfz : 身份证
             * hkb : 户口本
             * jhz : 结婚证
             * gzzm : 工作证明
             * srzm : 收入证明
             * gtz : 国土证
             * fcz : 房产证
             * fwsd : 房屋实地认证
             * zxbg : 征信报告
             * qsdc : 亲属调查
             * xsz : 行驶证
             * cldj : 车辆登记证
             * cldjfp : 车辆登记发票
             * cljq : 车辆交强险
             * clsy : 车辆商业保险
             * clpg : 车辆评估认证
             * yhls : 银行流水
             */

            private CompanyTypeBean company_type;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getWork_city() {
                return work_city;
            }

            public void setWork_city(String work_city) {
                this.work_city = work_city;
            }

            public String getIs_house() {
                return is_house;
            }

            public void setIs_house(String is_house) {
                this.is_house = is_house;
            }

            public String getIs_car() {
                return is_car;
            }

            public void setIs_car(String is_car) {
                this.is_car = is_car;
            }

            public String getCompany_position() {
                return company_position;
            }

            public void setCompany_position(String company_position) {
                this.company_position = company_position;
            }

            public String getWork_year() {
                return work_year;
            }

            public void setWork_year(String work_year) {
                this.work_year = work_year;
            }

            public CompanyTypeBean getCompany_type() {
                return company_type;
            }

            public void setCompany_type(CompanyTypeBean company_type) {
                this.company_type = company_type;
            }

            public static class CompanyTypeBean {
                private String sfz;
                private String hkb;
                private String jhz;
                private String gzzm;
                private String srzm;
                private String gtz;
                private String fcz;
                private String fwsd;
                private String zxbg;
                private String qsdc;
                private String xsz;
                private String cldj;
                private String cldjfp;
                private String cljq;
                private String clsy;
                private String clpg;
                private String yhls;

                public String getSfz() {
                    return sfz;
                }

                public void setSfz(String sfz) {
                    this.sfz = sfz;
                }

                public String getHkb() {
                    return hkb;
                }

                public void setHkb(String hkb) {
                    this.hkb = hkb;
                }

                public String getJhz() {
                    return jhz;
                }

                public void setJhz(String jhz) {
                    this.jhz = jhz;
                }

                public String getGzzm() {
                    return gzzm;
                }

                public void setGzzm(String gzzm) {
                    this.gzzm = gzzm;
                }

                public String getSrzm() {
                    return srzm;
                }

                public void setSrzm(String srzm) {
                    this.srzm = srzm;
                }

                public String getGtz() {
                    return gtz;
                }

                public void setGtz(String gtz) {
                    this.gtz = gtz;
                }

                public String getFcz() {
                    return fcz;
                }

                public void setFcz(String fcz) {
                    this.fcz = fcz;
                }

                public String getFwsd() {
                    return fwsd;
                }

                public void setFwsd(String fwsd) {
                    this.fwsd = fwsd;
                }

                public String getZxbg() {
                    return zxbg;
                }

                public void setZxbg(String zxbg) {
                    this.zxbg = zxbg;
                }

                public String getQsdc() {
                    return qsdc;
                }

                public void setQsdc(String qsdc) {
                    this.qsdc = qsdc;
                }

                public String getXsz() {
                    return xsz;
                }

                public void setXsz(String xsz) {
                    this.xsz = xsz;
                }

                public String getCldj() {
                    return cldj;
                }

                public void setCldj(String cldj) {
                    this.cldj = cldj;
                }

                public String getCldjfp() {
                    return cldjfp;
                }

                public void setCldjfp(String cldjfp) {
                    this.cldjfp = cldjfp;
                }

                public String getCljq() {
                    return cljq;
                }

                public void setCljq(String cljq) {
                    this.cljq = cljq;
                }

                public String getClsy() {
                    return clsy;
                }

                public void setClsy(String clsy) {
                    this.clsy = clsy;
                }

                public String getClpg() {
                    return clpg;
                }

                public void setClpg(String clpg) {
                    this.clpg = clpg;
                }

                public String getYhls() {
                    return yhls;
                }

                public void setYhls(String yhls) {
                    this.yhls = yhls;
                }
            }
        }
    }
}
