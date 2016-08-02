package com.ayd.rhcf.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yxd on 2016/7/26.
 */
public class OldBorrowBean implements Serializable {

    /**
     * code : 0
     * msg : 获取列表成功
     * data : {"name":"企融宝05001","invest_type":"3","user_id":"151386","borrow_type":"pawn","borrow_nid":"20160502003","account":"200000.00","borrow_apr":"14.10","borrow_period_name":"12个月","_borrow_end_time":-6725768,"style_title":"按月付息到期还本","borrow_status_nid":"repay","borrow_account_wait":0,"borrow_account_scale":"100.00","borrow_pawn_type":"房产","borrow_pawn_value":"7000000元","borrow_pawn_time":"2014年","borrow_pawn_xin":"2000000元","borrow_pawn_description":"该企业注册成立于2006年，注册资本260万元，主要从事家具、装饰材料等产品的生产销售。企业名下有三处厂房，位于合肥市肥东县，建筑面积共7000多平方米，目前市场估值在700万元左右。企业生产状况及销售情况良好。申请借款用于企业生产资金周转，为期12个月。\n","username":"13053057276","status_type_name":"还款中","award_status":"0","award_account":0,"tender_account_min":"100","tender_account_max":"0","tender_time":"1462188600","user_info":{"result":"success","module":"borrow","error_code":"","error_msg":"","error_remark":"","request":"&q=get_cominfo_one","id":"131","borrow_nid":"20160502003","user_id":"0","base_infomation":"114","ecnomic_ablity":"113","credit_status":"127","out_research":"150","object_evaluate":"121","professional_status":"115","ronghe_check":"2016年4月26日合作机构申请债权转让融资，当天风控部门就此项债权进行项目初审，并于4月27日去实地考察。经过DPS专业风控审核，风控部考察结果为：该公司在银行的征信状况良好，公司经营收益稳定。综合评分740分，为AA级。风控部门对借款项目表示风险可控，年营运收入可覆盖借款项目，评估为风险系数低,。4月29日进行了项目终审，当日手续办理完成。","info_time":"2016-04-26","project1_time":"2016-04-26","real_time":"2016-04-27","dsp_time":"2016-04-28","security_time":"2016-04-28","project2_time":"2016-04-29","mortgage_time":"2016-04-29","tender_full_time":null,"repay_yes":null,"start_time":null,"remain_time":null,"ronghe_third":"本项目由融和贷合作机构安徽巨元投资管理有限公司推荐提供。由安徽巨元投资管理有限公司法人先行放款给借款人，并签订相关合同、协议等法律文件；出借人取得合法债权后，在融和贷平台进行债权转让融资；债权转让人与合作方承担连带担保责任，并承诺在该项目到期前无条件回购。\n","username":"13053057276","realname":"刘**","sex":"男","card_id":"2103***3813","birthday":"1987年06月22日","marry":"未婚","degree":null,"edu":"本科","work_city":"合肥","is_house":"有房","is_car":"有车","company_position":"私营企业主","work_year":"三年以上","company_type":[],"zongxinyong":740,"_result":"\"{\\\"result\\\":\\\"success\\\",\\\"module\\\":\\\"borrow\\\",\\\"error_code\\\":\\\"\\\",\\\"error_msg\\\":\\\"\\\",\\\"error_remark\\\":\\\"\\\",\\\"request\\\":\\\"&q=get_cominfo_one\\\",\\\"id\\\":\\\"131\\\",\\\"borrow_nid\\\":\\\"20160502003\\\",\\\"user_id\\\":\\\"0\\\",\\\"base_infomation\\\":\\\"114\\\",\\\"ecnomic_ablity\\\":\\\"113\\\",\\\"credit_status\\\":\\\"127\\\",\\\"out_research\\\":\\\"150\\\",\\\"object_evaluate\\\":\\\"121\\\",\\\"professional_status\\\":\\\"115\\\",\\\"ronghe_check\\\":\\\"2016\\\\u5e744\\\\u670826\\\\u65e5\\\\u5408\\\\u4f5c\\\\u673a\\\\u6784\\\\u7533\\\\u8bf7\\\\u503a\\\\u6743\\\\u8f6c\\\\u8ba9\\\\u878d\\\\u8d44\\\\uff0c\\\\u5f53\\\\u5929\\\\u98ce\\\\u63a7\\\\u90e8\\\\u95e8\\\\u5c31\\\\u6b64\\\\u9879\\\\u503a\\\\u6743\\\\u8fdb\\\\u884c\\\\u9879\\\\u76ee\\\\u521d\\\\u5ba1\\\\uff0c\\\\u5e76\\\\u4e8e4\\\\u670827\\\\u65e5\\\\u53bb\\\\u5b9e\\\\u5730\\\\u8003\\\\u5bdf\\\\u3002\\\\u7ecf\\\\u8fc7DPS\\\\u4e13\\\\u4e1a\\\\u98ce\\\\u63a7\\\\u5ba1\\\\u6838\\\\uff0c\\\\u98ce\\\\u63a7\\\\u90e8\\\\u8003\\\\u5bdf\\\\u7ed3\\\\u679c\\\\u4e3a\\\\uff1a\\\\u8be5\\\\u516c\\\\u53f8\\\\u5728\\\\u94f6\\\\u884c\\\\u7684\\\\u5f81\\\\u4fe1\\\\u72b6\\\\u51b5\\\\u826f\\\\u597d\\\\uff0c\\\\u516c\\\\u53f8\\\\u7ecf\\\\u8425\\\\u6536\\\\u76ca\\\\u7a33\\\\u5b9a\\\\u3002\\\\u7efc\\\\u5408\\\\u8bc4\\\\u5206740\\\\u5206\\\\uff0c\\\\u4e3aAA\\\\u7ea7\\\\u3002\\\\u98ce\\\\u63a7\\\\u90e8\\\\u95e8\\\\u5bf9\\\\u501f\\\\u6b3e\\\\u9879\\\\u76ee\\\\u8868\\\\u793a\\\\u98ce\\\\u9669\\\\u53ef\\\\u63a7\\\\uff0c\\\\u5e74\\\\u8425\\\\u8fd0\\\\u6536\\\\u5165\\\\u53ef\\\\u8986\\\\u76d6\\\\u501f\\\\u6b3e\\\\u9879\\\\u76ee\\\\uff0c\\\\u8bc4\\\\u4f30\\\\u4e3a\\\\u98ce\\\\u9669\\\\u7cfb\\\\u6570\\\\u4f4e,\\\\u30024\\\\u670829\\\\u65e5\\\\u8fdb\\\\u884c\\\\u4e86\\\\u9879\\\\u76ee\\\\u7ec8\\\\u5ba1\\\\uff0c\\\\u5f53\\\\u65e5\\\\u624b\\\\u7eed\\\\u529e\\\\u7406\\\\u5b8c\\\\u6210\\\\u3002\\\",\\\"info_time\\\":\\\"2016-04-26\\\",\\\"project1_time\\\":\\\"2016-04-26\\\",\\\"real_time\\\":\\\"2016-04-27\\\",\\\"dsp_time\\\":\\\"2016-04-28\\\",\\\"security_time\\\":\\\"2016-04-28\\\",\\\"project2_time\\\":\\\"2016-04-29\\\",\\\"mortgage_time\\\":\\\"2016-04-29\\\",\\\"tender_full_time\\\":null,\\\"repay_yes\\\":null,\\\"start_time\\\":null,\\\"remain_time\\\":null,\\\"ronghe_third\\\":\\\"\\\\u672c\\\\u9879\\\\u76ee\\\\u7531\\\\u878d\\\\u548c\\\\u8d37\\\\u5408\\\\u4f5c\\\\u673a\\\\u6784\\\\u5b89\\\\u5fbd\\\\u5de8\\\\u5143\\\\u6295\\\\u8d44\\\\u7ba1\\\\u7406\\\\u6709\\\\u9650\\\\u516c\\\\u53f8\\\\u63a8\\\\u8350\\\\u63d0\\\\u4f9b\\\\u3002\\\\u7531\\\\u5b89\\\\u5fbd\\\\u5de8\\\\u5143\\\\u6295\\\\u8d44\\\\u7ba1\\\\u7406\\\\u6709\\\\u9650\\\\u516c\\\\u53f8\\\\u6cd5\\\\u4eba\\\\u5148\\\\u884c\\\\u653e\\\\u6b3e\\\\u7ed9\\\\u501f\\\\u6b3e\\\\u4eba\\\\uff0c\\\\u5e76\\\\u7b7e\\\\u8ba2\\\\u76f8\\\\u5173\\\\u5408\\\\u540c\\\\u3001\\\\u534f\\\\u8bae\\\\u7b49\\\\u6cd5\\\\u5f8b\\\\u6587\\\\u4ef6\\\\uff1b\\\\u51fa\\\\u501f\\\\u4eba\\\\u53d6\\\\u5f97\\\\u5408\\\\u6cd5\\\\u503a\\\\u6743\\\\u540e\\\\uff0c\\\\u5728\\\\u878d\\\\u548c\\\\u8d37\\\\u5e73\\\\u53f0\\\\u8fdb\\\\u884c\\\\u503a\\\\u6743\\\\u8f6c\\\\u8ba9\\\\u878d\\\\u8d44\\\\uff1b\\\\u503a\\\\u6743\\\\u8f6c\\\\u8ba9\\\\u4eba\\\\u4e0e\\\\u5408\\\\u4f5c\\\\u65b9\\\\u627f\\\\u62c5\\\\u8fde\\\\u5e26\\\\u62c5\\\\u4fdd\\\\u8d23\\\\u4efb\\\\uff0c\\\\u5e76\\\\u627f\\\\u8bfa\\\\u5728\\\\u8be5\\\\u9879\\\\u76ee\\\\u5230\\\\u671f\\\\u524d\\\\u65e0\\\\u6761\\\\u4ef6\\\\u56de\\\\u8d2d\\\\u3002\\\\n\\\",\\\"username\\\":\\\"liuqiang\\\",\\\"realname\\\":\\\"\\\\u5218\\\\u5f3a\\\",\\\"sex\\\":\\\"1\\\",\\\"card_id\\\":\\\"210381198706223813\\\",\\\"birthday\\\":\\\"1987-06-22\\\",\\\"marry\\\":\\\"1\\\",\\\"degree\\\":null,\\\"edu\\\":\\\"5\\\",\\\"work_city\\\":\\\"\\\\u5408\\\\u80a5\\\",\\\"is_house\\\":\\\"1\\\",\\\"is_car\\\":\\\"1\\\",\\\"company_position\\\":\\\"9\\\",\\\"work_year\\\":\\\"5\\\",\\\"company_type\\\":[],\\\"zongxinyong\\\":740}\"","phone":"***"},"view_type":"1","open_tender":{"id":"1","name":"安徽巨元投资管理有限公司","company_serialno":"340100000894243","legal":"毛和平","status":"1","cooperation":"/dyupfiles/images/2016-06/07/0_admin_upload_1465270107990.jpg","repo":"/dyupfiles/images/2016-06/07/0_admin_upload_1465270117587.jpg","content":"安徽巨元投资管理有限公司成立于2014年1月13日，公司主要进行投资管理，投资咨询，楼盘代理营销策划，房产买卖、二手车买卖业务。本项目由融和贷合作机构安徽巨元投资管理有限公司推荐提供。由安徽巨元投资管理有限公司与借款人签订相关合同、协议等法律文件并先行放款；出借人取得合法债权后，在融和贷平台进行债权转让融资；债权转让人与合作方承担连带担保责任，并承诺在该项目到期前无条件回购。"},"cominfo":[],"ronghe_third":"本项目由融和贷合作机构安徽巨元投资管理有限公司推荐提供。由安徽巨元投资管理有限公司法人先行放款给借款人，并签订相关合同、协议等法律文件；出借人取得合法债权后，在融和贷平台进行债权转让融资；债权转让人与合作方承担连带担保责任，并承诺在该项目到期前无条件回购。\n","upfiles_pic":["/dyupfiles/images/2016-05/02/0_admin_upload_1462168509147.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168511677.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168512491.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168512918.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168513612.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168515554.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168516312.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168517229.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168520970.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168523930.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168525135.jpg"]}
     */

    private String code;
    private String msg;
    /**
     * name : 企融宝05001
     * invest_type : 3
     * user_id : 151386
     * borrow_type : pawn
     * borrow_nid : 20160502003
     * account : 200000.00
     * borrow_apr : 14.10
     * borrow_period_name : 12个月
     * _borrow_end_time : -6725768
     * style_title : 按月付息到期还本
     * borrow_status_nid : repay
     * borrow_account_wait : 0
     * borrow_account_scale : 100.00
     * borrow_pawn_type : 房产
     * borrow_pawn_value : 7000000元
     * borrow_pawn_time : 2014年
     * borrow_pawn_xin : 2000000元
     * borrow_pawn_description : 该企业注册成立于2006年，注册资本260万元，主要从事家具、装饰材料等产品的生产销售。企业名下有三处厂房，位于合肥市肥东县，建筑面积共7000多平方米，目前市场估值在700万元左右。企业生产状况及销售情况良好。申请借款用于企业生产资金周转，为期12个月。

     * username : 13053057276
     * status_type_name : 还款中
     * award_status : 0
     * award_account : 0
     * tender_account_min : 100
     * tender_account_max : 0
     * tender_time : 1462188600
     * user_info : {"result":"success","module":"borrow","error_code":"","error_msg":"","error_remark":"","request":"&q=get_cominfo_one","id":"131","borrow_nid":"20160502003","user_id":"0","base_infomation":"114","ecnomic_ablity":"113","credit_status":"127","out_research":"150","object_evaluate":"121","professional_status":"115","ronghe_check":"2016年4月26日合作机构申请债权转让融资，当天风控部门就此项债权进行项目初审，并于4月27日去实地考察。经过DPS专业风控审核，风控部考察结果为：该公司在银行的征信状况良好，公司经营收益稳定。综合评分740分，为AA级。风控部门对借款项目表示风险可控，年营运收入可覆盖借款项目，评估为风险系数低,。4月29日进行了项目终审，当日手续办理完成。","info_time":"2016-04-26","project1_time":"2016-04-26","real_time":"2016-04-27","dsp_time":"2016-04-28","security_time":"2016-04-28","project2_time":"2016-04-29","mortgage_time":"2016-04-29","tender_full_time":null,"repay_yes":null,"start_time":null,"remain_time":null,"ronghe_third":"本项目由融和贷合作机构安徽巨元投资管理有限公司推荐提供。由安徽巨元投资管理有限公司法人先行放款给借款人，并签订相关合同、协议等法律文件；出借人取得合法债权后，在融和贷平台进行债权转让融资；债权转让人与合作方承担连带担保责任，并承诺在该项目到期前无条件回购。\n","username":"13053057276","realname":"刘**","sex":"男","card_id":"2103***3813","birthday":"1987年06月22日","marry":"未婚","degree":null,"edu":"本科","work_city":"合肥","is_house":"有房","is_car":"有车","company_position":"私营企业主","work_year":"三年以上","company_type":[],"zongxinyong":740,"_result":"\"{\\\"result\\\":\\\"success\\\",\\\"module\\\":\\\"borrow\\\",\\\"error_code\\\":\\\"\\\",\\\"error_msg\\\":\\\"\\\",\\\"error_remark\\\":\\\"\\\",\\\"request\\\":\\\"&q=get_cominfo_one\\\",\\\"id\\\":\\\"131\\\",\\\"borrow_nid\\\":\\\"20160502003\\\",\\\"user_id\\\":\\\"0\\\",\\\"base_infomation\\\":\\\"114\\\",\\\"ecnomic_ablity\\\":\\\"113\\\",\\\"credit_status\\\":\\\"127\\\",\\\"out_research\\\":\\\"150\\\",\\\"object_evaluate\\\":\\\"121\\\",\\\"professional_status\\\":\\\"115\\\",\\\"ronghe_check\\\":\\\"2016\\\\u5e744\\\\u670826\\\\u65e5\\\\u5408\\\\u4f5c\\\\u673a\\\\u6784\\\\u7533\\\\u8bf7\\\\u503a\\\\u6743\\\\u8f6c\\\\u8ba9\\\\u878d\\\\u8d44\\\\uff0c\\\\u5f53\\\\u5929\\\\u98ce\\\\u63a7\\\\u90e8\\\\u95e8\\\\u5c31\\\\u6b64\\\\u9879\\\\u503a\\\\u6743\\\\u8fdb\\\\u884c\\\\u9879\\\\u76ee\\\\u521d\\\\u5ba1\\\\uff0c\\\\u5e76\\\\u4e8e4\\\\u670827\\\\u65e5\\\\u53bb\\\\u5b9e\\\\u5730\\\\u8003\\\\u5bdf\\\\u3002\\\\u7ecf\\\\u8fc7DPS\\\\u4e13\\\\u4e1a\\\\u98ce\\\\u63a7\\\\u5ba1\\\\u6838\\\\uff0c\\\\u98ce\\\\u63a7\\\\u90e8\\\\u8003\\\\u5bdf\\\\u7ed3\\\\u679c\\\\u4e3a\\\\uff1a\\\\u8be5\\\\u516c\\\\u53f8\\\\u5728\\\\u94f6\\\\u884c\\\\u7684\\\\u5f81\\\\u4fe1\\\\u72b6\\\\u51b5\\\\u826f\\\\u597d\\\\uff0c\\\\u516c\\\\u53f8\\\\u7ecf\\\\u8425\\\\u6536\\\\u76ca\\\\u7a33\\\\u5b9a\\\\u3002\\\\u7efc\\\\u5408\\\\u8bc4\\\\u5206740\\\\u5206\\\\uff0c\\\\u4e3aAA\\\\u7ea7\\\\u3002\\\\u98ce\\\\u63a7\\\\u90e8\\\\u95e8\\\\u5bf9\\\\u501f\\\\u6b3e\\\\u9879\\\\u76ee\\\\u8868\\\\u793a\\\\u98ce\\\\u9669\\\\u53ef\\\\u63a7\\\\uff0c\\\\u5e74\\\\u8425\\\\u8fd0\\\\u6536\\\\u5165\\\\u53ef\\\\u8986\\\\u76d6\\\\u501f\\\\u6b3e\\\\u9879\\\\u76ee\\\\uff0c\\\\u8bc4\\\\u4f30\\\\u4e3a\\\\u98ce\\\\u9669\\\\u7cfb\\\\u6570\\\\u4f4e,\\\\u30024\\\\u670829\\\\u65e5\\\\u8fdb\\\\u884c\\\\u4e86\\\\u9879\\\\u76ee\\\\u7ec8\\\\u5ba1\\\\uff0c\\\\u5f53\\\\u65e5\\\\u624b\\\\u7eed\\\\u529e\\\\u7406\\\\u5b8c\\\\u6210\\\\u3002\\\",\\\"info_time\\\":\\\"2016-04-26\\\",\\\"project1_time\\\":\\\"2016-04-26\\\",\\\"real_time\\\":\\\"2016-04-27\\\",\\\"dsp_time\\\":\\\"2016-04-28\\\",\\\"security_time\\\":\\\"2016-04-28\\\",\\\"project2_time\\\":\\\"2016-04-29\\\",\\\"mortgage_time\\\":\\\"2016-04-29\\\",\\\"tender_full_time\\\":null,\\\"repay_yes\\\":null,\\\"start_time\\\":null,\\\"remain_time\\\":null,\\\"ronghe_third\\\":\\\"\\\\u672c\\\\u9879\\\\u76ee\\\\u7531\\\\u878d\\\\u548c\\\\u8d37\\\\u5408\\\\u4f5c\\\\u673a\\\\u6784\\\\u5b89\\\\u5fbd\\\\u5de8\\\\u5143\\\\u6295\\\\u8d44\\\\u7ba1\\\\u7406\\\\u6709\\\\u9650\\\\u516c\\\\u53f8\\\\u63a8\\\\u8350\\\\u63d0\\\\u4f9b\\\\u3002\\\\u7531\\\\u5b89\\\\u5fbd\\\\u5de8\\\\u5143\\\\u6295\\\\u8d44\\\\u7ba1\\\\u7406\\\\u6709\\\\u9650\\\\u516c\\\\u53f8\\\\u6cd5\\\\u4eba\\\\u5148\\\\u884c\\\\u653e\\\\u6b3e\\\\u7ed9\\\\u501f\\\\u6b3e\\\\u4eba\\\\uff0c\\\\u5e76\\\\u7b7e\\\\u8ba2\\\\u76f8\\\\u5173\\\\u5408\\\\u540c\\\\u3001\\\\u534f\\\\u8bae\\\\u7b49\\\\u6cd5\\\\u5f8b\\\\u6587\\\\u4ef6\\\\uff1b\\\\u51fa\\\\u501f\\\\u4eba\\\\u53d6\\\\u5f97\\\\u5408\\\\u6cd5\\\\u503a\\\\u6743\\\\u540e\\\\uff0c\\\\u5728\\\\u878d\\\\u548c\\\\u8d37\\\\u5e73\\\\u53f0\\\\u8fdb\\\\u884c\\\\u503a\\\\u6743\\\\u8f6c\\\\u8ba9\\\\u878d\\\\u8d44\\\\uff1b\\\\u503a\\\\u6743\\\\u8f6c\\\\u8ba9\\\\u4eba\\\\u4e0e\\\\u5408\\\\u4f5c\\\\u65b9\\\\u627f\\\\u62c5\\\\u8fde\\\\u5e26\\\\u62c5\\\\u4fdd\\\\u8d23\\\\u4efb\\\\uff0c\\\\u5e76\\\\u627f\\\\u8bfa\\\\u5728\\\\u8be5\\\\u9879\\\\u76ee\\\\u5230\\\\u671f\\\\u524d\\\\u65e0\\\\u6761\\\\u4ef6\\\\u56de\\\\u8d2d\\\\u3002\\\\n\\\",\\\"username\\\":\\\"liuqiang\\\",\\\"realname\\\":\\\"\\\\u5218\\\\u5f3a\\\",\\\"sex\\\":\\\"1\\\",\\\"card_id\\\":\\\"210381198706223813\\\",\\\"birthday\\\":\\\"1987-06-22\\\",\\\"marry\\\":\\\"1\\\",\\\"degree\\\":null,\\\"edu\\\":\\\"5\\\",\\\"work_city\\\":\\\"\\\\u5408\\\\u80a5\\\",\\\"is_house\\\":\\\"1\\\",\\\"is_car\\\":\\\"1\\\",\\\"company_position\\\":\\\"9\\\",\\\"work_year\\\":\\\"5\\\",\\\"company_type\\\":[],\\\"zongxinyong\\\":740}\"","phone":"***"}
     * view_type : 1
     * open_tender : {"id":"1","name":"安徽巨元投资管理有限公司","company_serialno":"340100000894243","legal":"毛和平","status":"1","cooperation":"/dyupfiles/images/2016-06/07/0_admin_upload_1465270107990.jpg","repo":"/dyupfiles/images/2016-06/07/0_admin_upload_1465270117587.jpg","content":"安徽巨元投资管理有限公司成立于2014年1月13日，公司主要进行投资管理，投资咨询，楼盘代理营销策划，房产买卖、二手车买卖业务。本项目由融和贷合作机构安徽巨元投资管理有限公司推荐提供。由安徽巨元投资管理有限公司与借款人签订相关合同、协议等法律文件并先行放款；出借人取得合法债权后，在融和贷平台进行债权转让融资；债权转让人与合作方承担连带担保责任，并承诺在该项目到期前无条件回购。"}
     * cominfo : []
     * ronghe_third : 本项目由融和贷合作机构安徽巨元投资管理有限公司推荐提供。由安徽巨元投资管理有限公司法人先行放款给借款人，并签订相关合同、协议等法律文件；出借人取得合法债权后，在融和贷平台进行债权转让融资；债权转让人与合作方承担连带担保责任，并承诺在该项目到期前无条件回购。

     * upfiles_pic : ["/dyupfiles/images/2016-05/02/0_admin_upload_1462168509147.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168511677.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168512491.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168512918.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168513612.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168515554.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168516312.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168517229.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168520970.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168523930.jpg","/dyupfiles/images/2016-05/02/0_admin_upload_1462168525135.jpg"]
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
        private String name;
        private String invest_type;
        private String user_id;
        private String borrow_type;
        private String borrow_nid;
        private String account;
        private String borrow_apr;
        private String borrow_period_name;
        private int _borrow_end_time;
        private String style_title;
        private String borrow_status_nid;
        private int borrow_account_wait;
        private String borrow_account_scale;
        private String borrow_pawn_type;
        private String borrow_pawn_value;
        private String borrow_pawn_time;
        private String borrow_pawn_xin;
        private String borrow_pawn_description;
        private String username;
        private String status_type_name;
        private String award_status;
        private int award_account;
        private String tender_account_min;
        private String tender_account_max;
        private String tender_time;
        /**
         * result : success
         * module : borrow
         * error_code :
         * error_msg :
         * error_remark :
         * request : &q=get_cominfo_one
         * id : 131
         * borrow_nid : 20160502003
         * user_id : 0
         * base_infomation : 114
         * ecnomic_ablity : 113
         * credit_status : 127
         * out_research : 150
         * object_evaluate : 121
         * professional_status : 115
         * ronghe_check : 2016年4月26日合作机构申请债权转让融资，当天风控部门就此项债权进行项目初审，并于4月27日去实地考察。经过DPS专业风控审核，风控部考察结果为：该公司在银行的征信状况良好，公司经营收益稳定。综合评分740分，为AA级。风控部门对借款项目表示风险可控，年营运收入可覆盖借款项目，评估为风险系数低,。4月29日进行了项目终审，当日手续办理完成。
         * info_time : 2016-04-26
         * project1_time : 2016-04-26
         * real_time : 2016-04-27
         * dsp_time : 2016-04-28
         * security_time : 2016-04-28
         * project2_time : 2016-04-29
         * mortgage_time : 2016-04-29
         * tender_full_time : null
         * repay_yes : null
         * start_time : null
         * remain_time : null
         * ronghe_third : 本项目由融和贷合作机构安徽巨元投资管理有限公司推荐提供。由安徽巨元投资管理有限公司法人先行放款给借款人，并签订相关合同、协议等法律文件；出借人取得合法债权后，在融和贷平台进行债权转让融资；债权转让人与合作方承担连带担保责任，并承诺在该项目到期前无条件回购。

         * username : 13053057276
         * realname : 刘**
         * sex : 男
         * card_id : 2103***3813
         * birthday : 1987年06月22日
         * marry : 未婚
         * degree : null
         * edu : 本科
         * work_city : 合肥
         * is_house : 有房
         * is_car : 有车
         * company_position : 私营企业主
         * work_year : 三年以上
         * company_type : []
         * zongxinyong : 740
         * _result : "{\"result\":\"success\",\"module\":\"borrow\",\"error_code\":\"\",\"error_msg\":\"\",\"error_remark\":\"\",\"request\":\"&q=get_cominfo_one\",\"id\":\"131\",\"borrow_nid\":\"20160502003\",\"user_id\":\"0\",\"base_infomation\":\"114\",\"ecnomic_ablity\":\"113\",\"credit_status\":\"127\",\"out_research\":\"150\",\"object_evaluate\":\"121\",\"professional_status\":\"115\",\"ronghe_check\":\"2016\\u5e744\\u670826\\u65e5\\u5408\\u4f5c\\u673a\\u6784\\u7533\\u8bf7\\u503a\\u6743\\u8f6c\\u8ba9\\u878d\\u8d44\\uff0c\\u5f53\\u5929\\u98ce\\u63a7\\u90e8\\u95e8\\u5c31\\u6b64\\u9879\\u503a\\u6743\\u8fdb\\u884c\\u9879\\u76ee\\u521d\\u5ba1\\uff0c\\u5e76\\u4e8e4\\u670827\\u65e5\\u53bb\\u5b9e\\u5730\\u8003\\u5bdf\\u3002\\u7ecf\\u8fc7DPS\\u4e13\\u4e1a\\u98ce\\u63a7\\u5ba1\\u6838\\uff0c\\u98ce\\u63a7\\u90e8\\u8003\\u5bdf\\u7ed3\\u679c\\u4e3a\\uff1a\\u8be5\\u516c\\u53f8\\u5728\\u94f6\\u884c\\u7684\\u5f81\\u4fe1\\u72b6\\u51b5\\u826f\\u597d\\uff0c\\u516c\\u53f8\\u7ecf\\u8425\\u6536\\u76ca\\u7a33\\u5b9a\\u3002\\u7efc\\u5408\\u8bc4\\u5206740\\u5206\\uff0c\\u4e3aAA\\u7ea7\\u3002\\u98ce\\u63a7\\u90e8\\u95e8\\u5bf9\\u501f\\u6b3e\\u9879\\u76ee\\u8868\\u793a\\u98ce\\u9669\\u53ef\\u63a7\\uff0c\\u5e74\\u8425\\u8fd0\\u6536\\u5165\\u53ef\\u8986\\u76d6\\u501f\\u6b3e\\u9879\\u76ee\\uff0c\\u8bc4\\u4f30\\u4e3a\\u98ce\\u9669\\u7cfb\\u6570\\u4f4e,\\u30024\\u670829\\u65e5\\u8fdb\\u884c\\u4e86\\u9879\\u76ee\\u7ec8\\u5ba1\\uff0c\\u5f53\\u65e5\\u624b\\u7eed\\u529e\\u7406\\u5b8c\\u6210\\u3002\",\"info_time\":\"2016-04-26\",\"project1_time\":\"2016-04-26\",\"real_time\":\"2016-04-27\",\"dsp_time\":\"2016-04-28\",\"security_time\":\"2016-04-28\",\"project2_time\":\"2016-04-29\",\"mortgage_time\":\"2016-04-29\",\"tender_full_time\":null,\"repay_yes\":null,\"start_time\":null,\"remain_time\":null,\"ronghe_third\":\"\\u672c\\u9879\\u76ee\\u7531\\u878d\\u548c\\u8d37\\u5408\\u4f5c\\u673a\\u6784\\u5b89\\u5fbd\\u5de8\\u5143\\u6295\\u8d44\\u7ba1\\u7406\\u6709\\u9650\\u516c\\u53f8\\u63a8\\u8350\\u63d0\\u4f9b\\u3002\\u7531\\u5b89\\u5fbd\\u5de8\\u5143\\u6295\\u8d44\\u7ba1\\u7406\\u6709\\u9650\\u516c\\u53f8\\u6cd5\\u4eba\\u5148\\u884c\\u653e\\u6b3e\\u7ed9\\u501f\\u6b3e\\u4eba\\uff0c\\u5e76\\u7b7e\\u8ba2\\u76f8\\u5173\\u5408\\u540c\\u3001\\u534f\\u8bae\\u7b49\\u6cd5\\u5f8b\\u6587\\u4ef6\\uff1b\\u51fa\\u501f\\u4eba\\u53d6\\u5f97\\u5408\\u6cd5\\u503a\\u6743\\u540e\\uff0c\\u5728\\u878d\\u548c\\u8d37\\u5e73\\u53f0\\u8fdb\\u884c\\u503a\\u6743\\u8f6c\\u8ba9\\u878d\\u8d44\\uff1b\\u503a\\u6743\\u8f6c\\u8ba9\\u4eba\\u4e0e\\u5408\\u4f5c\\u65b9\\u627f\\u62c5\\u8fde\\u5e26\\u62c5\\u4fdd\\u8d23\\u4efb\\uff0c\\u5e76\\u627f\\u8bfa\\u5728\\u8be5\\u9879\\u76ee\\u5230\\u671f\\u524d\\u65e0\\u6761\\u4ef6\\u56de\\u8d2d\\u3002\\n\",\"username\":\"liuqiang\",\"realname\":\"\\u5218\\u5f3a\",\"sex\":\"1\",\"card_id\":\"210381198706223813\",\"birthday\":\"1987-06-22\",\"marry\":\"1\",\"degree\":null,\"edu\":\"5\",\"work_city\":\"\\u5408\\u80a5\",\"is_house\":\"1\",\"is_car\":\"1\",\"company_position\":\"9\",\"work_year\":\"5\",\"company_type\":[],\"zongxinyong\":740}"
         * phone : ***
         */

        private UserInfoBean user_info;
        private String view_type;
        /**
         * id : 1
         * name : 安徽巨元投资管理有限公司
         * company_serialno : 340100000894243
         * legal : 毛和平
         * status : 1
         * cooperation : /dyupfiles/images/2016-06/07/0_admin_upload_1465270107990.jpg
         * repo : /dyupfiles/images/2016-06/07/0_admin_upload_1465270117587.jpg
         * content : 安徽巨元投资管理有限公司成立于2014年1月13日，公司主要进行投资管理，投资咨询，楼盘代理营销策划，房产买卖、二手车买卖业务。本项目由融和贷合作机构安徽巨元投资管理有限公司推荐提供。由安徽巨元投资管理有限公司与借款人签订相关合同、协议等法律文件并先行放款；出借人取得合法债权后，在融和贷平台进行债权转让融资；债权转让人与合作方承担连带担保责任，并承诺在该项目到期前无条件回购。
         */

        private OpenTenderBean open_tender;
        private String ronghe_third;
        private List<?> cominfo;
        private List<String> upfiles_pic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInvest_type() {
            return invest_type;
        }

        public void setInvest_type(String invest_type) {
            this.invest_type = invest_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBorrow_type() {
            return borrow_type;
        }

        public void setBorrow_type(String borrow_type) {
            this.borrow_type = borrow_type;
        }

        public String getBorrow_nid() {
            return borrow_nid;
        }

        public void setBorrow_nid(String borrow_nid) {
            this.borrow_nid = borrow_nid;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getBorrow_apr() {
            return borrow_apr;
        }

        public void setBorrow_apr(String borrow_apr) {
            this.borrow_apr = borrow_apr;
        }

        public String getBorrow_period_name() {
            return borrow_period_name;
        }

        public void setBorrow_period_name(String borrow_period_name) {
            this.borrow_period_name = borrow_period_name;
        }

        public int get_borrow_end_time() {
            return _borrow_end_time;
        }

        public void set_borrow_end_time(int _borrow_end_time) {
            this._borrow_end_time = _borrow_end_time;
        }

        public String getStyle_title() {
            return style_title;
        }

        public void setStyle_title(String style_title) {
            this.style_title = style_title;
        }

        public String getBorrow_status_nid() {
            return borrow_status_nid;
        }

        public void setBorrow_status_nid(String borrow_status_nid) {
            this.borrow_status_nid = borrow_status_nid;
        }

        public int getBorrow_account_wait() {
            return borrow_account_wait;
        }

        public void setBorrow_account_wait(int borrow_account_wait) {
            this.borrow_account_wait = borrow_account_wait;
        }

        public String getBorrow_account_scale() {
            return borrow_account_scale;
        }

        public void setBorrow_account_scale(String borrow_account_scale) {
            this.borrow_account_scale = borrow_account_scale;
        }

        public String getBorrow_pawn_type() {
            return borrow_pawn_type;
        }

        public void setBorrow_pawn_type(String borrow_pawn_type) {
            this.borrow_pawn_type = borrow_pawn_type;
        }

        public String getBorrow_pawn_value() {
            return borrow_pawn_value;
        }

        public void setBorrow_pawn_value(String borrow_pawn_value) {
            this.borrow_pawn_value = borrow_pawn_value;
        }

        public String getBorrow_pawn_time() {
            return borrow_pawn_time;
        }

        public void setBorrow_pawn_time(String borrow_pawn_time) {
            this.borrow_pawn_time = borrow_pawn_time;
        }

        public String getBorrow_pawn_xin() {
            return borrow_pawn_xin;
        }

        public void setBorrow_pawn_xin(String borrow_pawn_xin) {
            this.borrow_pawn_xin = borrow_pawn_xin;
        }

        public String getBorrow_pawn_description() {
            return borrow_pawn_description;
        }

        public void setBorrow_pawn_description(String borrow_pawn_description) {
            this.borrow_pawn_description = borrow_pawn_description;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStatus_type_name() {
            return status_type_name;
        }

        public void setStatus_type_name(String status_type_name) {
            this.status_type_name = status_type_name;
        }

        public String getAward_status() {
            return award_status;
        }

        public void setAward_status(String award_status) {
            this.award_status = award_status;
        }

        public int getAward_account() {
            return award_account;
        }

        public void setAward_account(int award_account) {
            this.award_account = award_account;
        }

        public String getTender_account_min() {
            return tender_account_min;
        }

        public void setTender_account_min(String tender_account_min) {
            this.tender_account_min = tender_account_min;
        }

        public String getTender_account_max() {
            return tender_account_max;
        }

        public void setTender_account_max(String tender_account_max) {
            this.tender_account_max = tender_account_max;
        }

        public String getTender_time() {
            return tender_time;
        }

        public void setTender_time(String tender_time) {
            this.tender_time = tender_time;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public String getView_type() {
            return view_type;
        }

        public void setView_type(String view_type) {
            this.view_type = view_type;
        }

        public OpenTenderBean getOpen_tender() {
            return open_tender;
        }

        public void setOpen_tender(OpenTenderBean open_tender) {
            this.open_tender = open_tender;
        }

        public String getRonghe_third() {
            return ronghe_third;
        }

        public void setRonghe_third(String ronghe_third) {
            this.ronghe_third = ronghe_third;
        }

        public List<?> getCominfo() {
            return cominfo;
        }

        public void setCominfo(List<?> cominfo) {
            this.cominfo = cominfo;
        }

        public List<String> getUpfiles_pic() {
            return upfiles_pic;
        }

        public void setUpfiles_pic(List<String> upfiles_pic) {
            this.upfiles_pic = upfiles_pic;
        }

        public static class UserInfoBean {
            private String result;
            private String module;
            private String error_code;
            private String error_msg;
            private String error_remark;
            private String request;
            private String id;
            private String borrow_nid;
            private String user_id;
            private String base_infomation;
            private String ecnomic_ablity;
            private String credit_status;
            private String out_research;
            private String object_evaluate;
            private String professional_status;
            private String ronghe_check;
            private String info_time;
            private String project1_time;
            private String real_time;
            private String dsp_time;
            private String security_time;
            private String project2_time;
            private String mortgage_time;
            private Object tender_full_time;
            private Object repay_yes;
            private Object start_time;
            private Object remain_time;
            private String ronghe_third;
            private String username;
            private String realname;
            private String sex;
            private String card_id;
            private String birthday;
            private String marry;
            private Object degree;
            private String edu;
            private String work_city;
            private String is_house;
            private String is_car;
            private String company_position;
            private String work_year;
            private int zongxinyong;
            private String _result;
            private String phone;
            private List<?> company_type;

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String getModule() {
                return module;
            }

            public void setModule(String module) {
                this.module = module;
            }

            public String getError_code() {
                return error_code;
            }

            public void setError_code(String error_code) {
                this.error_code = error_code;
            }

            public String getError_msg() {
                return error_msg;
            }

            public void setError_msg(String error_msg) {
                this.error_msg = error_msg;
            }

            public String getError_remark() {
                return error_remark;
            }

            public void setError_remark(String error_remark) {
                this.error_remark = error_remark;
            }

            public String getRequest() {
                return request;
            }

            public void setRequest(String request) {
                this.request = request;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getBorrow_nid() {
                return borrow_nid;
            }

            public void setBorrow_nid(String borrow_nid) {
                this.borrow_nid = borrow_nid;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getBase_infomation() {
                return base_infomation;
            }

            public void setBase_infomation(String base_infomation) {
                this.base_infomation = base_infomation;
            }

            public String getEcnomic_ablity() {
                return ecnomic_ablity;
            }

            public void setEcnomic_ablity(String ecnomic_ablity) {
                this.ecnomic_ablity = ecnomic_ablity;
            }

            public String getCredit_status() {
                return credit_status;
            }

            public void setCredit_status(String credit_status) {
                this.credit_status = credit_status;
            }

            public String getOut_research() {
                return out_research;
            }

            public void setOut_research(String out_research) {
                this.out_research = out_research;
            }

            public String getObject_evaluate() {
                return object_evaluate;
            }

            public void setObject_evaluate(String object_evaluate) {
                this.object_evaluate = object_evaluate;
            }

            public String getProfessional_status() {
                return professional_status;
            }

            public void setProfessional_status(String professional_status) {
                this.professional_status = professional_status;
            }

            public String getRonghe_check() {
                return ronghe_check;
            }

            public void setRonghe_check(String ronghe_check) {
                this.ronghe_check = ronghe_check;
            }

            public String getInfo_time() {
                return info_time;
            }

            public void setInfo_time(String info_time) {
                this.info_time = info_time;
            }

            public String getProject1_time() {
                return project1_time;
            }

            public void setProject1_time(String project1_time) {
                this.project1_time = project1_time;
            }

            public String getReal_time() {
                return real_time;
            }

            public void setReal_time(String real_time) {
                this.real_time = real_time;
            }

            public String getDsp_time() {
                return dsp_time;
            }

            public void setDsp_time(String dsp_time) {
                this.dsp_time = dsp_time;
            }

            public String getSecurity_time() {
                return security_time;
            }

            public void setSecurity_time(String security_time) {
                this.security_time = security_time;
            }

            public String getProject2_time() {
                return project2_time;
            }

            public void setProject2_time(String project2_time) {
                this.project2_time = project2_time;
            }

            public String getMortgage_time() {
                return mortgage_time;
            }

            public void setMortgage_time(String mortgage_time) {
                this.mortgage_time = mortgage_time;
            }

            public Object getTender_full_time() {
                return tender_full_time;
            }

            public void setTender_full_time(Object tender_full_time) {
                this.tender_full_time = tender_full_time;
            }

            public Object getRepay_yes() {
                return repay_yes;
            }

            public void setRepay_yes(Object repay_yes) {
                this.repay_yes = repay_yes;
            }

            public Object getStart_time() {
                return start_time;
            }

            public void setStart_time(Object start_time) {
                this.start_time = start_time;
            }

            public Object getRemain_time() {
                return remain_time;
            }

            public void setRemain_time(Object remain_time) {
                this.remain_time = remain_time;
            }

            public String getRonghe_third() {
                return ronghe_third;
            }

            public void setRonghe_third(String ronghe_third) {
                this.ronghe_third = ronghe_third;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getCard_id() {
                return card_id;
            }

            public void setCard_id(String card_id) {
                this.card_id = card_id;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getMarry() {
                return marry;
            }

            public void setMarry(String marry) {
                this.marry = marry;
            }

            public Object getDegree() {
                return degree;
            }

            public void setDegree(Object degree) {
                this.degree = degree;
            }

            public String getEdu() {
                return edu;
            }

            public void setEdu(String edu) {
                this.edu = edu;
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

            public int getZongxinyong() {
                return zongxinyong;
            }

            public void setZongxinyong(int zongxinyong) {
                this.zongxinyong = zongxinyong;
            }

            public String get_result() {
                return _result;
            }

            public void set_result(String _result) {
                this._result = _result;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public List<?> getCompany_type() {
                return company_type;
            }

            public void setCompany_type(List<?> company_type) {
                this.company_type = company_type;
            }
        }

        public static class OpenTenderBean {
            private String id;
            private String name;
            private String company_serialno;
            private String legal;
            private String status;
            private String cooperation;
            private String repo;
            private String content;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCompany_serialno() {
                return company_serialno;
            }

            public void setCompany_serialno(String company_serialno) {
                this.company_serialno = company_serialno;
            }

            public String getLegal() {
                return legal;
            }

            public void setLegal(String legal) {
                this.legal = legal;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCooperation() {
                return cooperation;
            }

            public void setCooperation(String cooperation) {
                this.cooperation = cooperation;
            }

            public String getRepo() {
                return repo;
            }

            public void setRepo(String repo) {
                this.repo = repo;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
