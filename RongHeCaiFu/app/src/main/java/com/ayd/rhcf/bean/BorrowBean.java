package com.ayd.rhcf.bean;

import java.io.Serializable;

/**
 * Created by yxd on 2016/7/18.
 */
public class BorrowBean implements Serializable{

    /**
     * account : 10000.00
     * award_status : 0
     * borrow_account_scale : 100.00
     * borrow_apr : 10.00
     * borrow_nid : 20160712001
     * borrow_period : 3
     * count_down : 0
     * invest_type : 0
     * name : 10000
     * tender_account_min : 1
     * tender_time : 0
     * status : 3
     * borrow_type : 测
     * borrow_account_wait : 0.00
     * borrow_end_time : 1468377349
     * now_time : 1468835264
     * borrow_style : month
     * _borrow_end_time : -457915
     * borrow_period_name : 3个月
     * borrow_status_nid : repay
     */

    private String account;
    private String award_status;
    private float borrow_account_scale;
    private String borrow_apr;
    private String borrow_nid;
    private String borrow_period;
    private String count_down;
    private String invest_type;
    private String name;
    private String tender_account_min;
    private String tender_time;
    private String status;
    private String borrow_type;
    private String borrow_account_wait;
    private String borrow_end_time;
    private long now_time;
    private int _borrow_end_time;
    private String borrow_period_name;
    private String borrow_status_nid;
    private String borrow_style;
    private String repay_type;

    public String getRepay_type() {
        return repay_type;
    }

    public void setRepay_type(String repay_type) {

        this.repay_type = repay_type;
    }

    public String getBorrow_style() {
        return borrow_style;
    }

    public void setBorrow_style(String borrow_style) {

        this.borrow_style = borrow_style;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAward_status() {
        return award_status;
    }

    public void setAward_status(String award_status) {
        this.award_status = award_status;
    }

    public float getBorrow_account_scale() {
        return borrow_account_scale;
    }

    public void setBorrow_account_scale(float borrow_account_scale) {
        this.borrow_account_scale = borrow_account_scale;
    }

    public String getBorrow_apr() {
        return borrow_apr;
    }

    public void setBorrow_apr(String borrow_apr) {
        this.borrow_apr = borrow_apr;
    }

    public String getBorrow_nid() {
        return borrow_nid;
    }

    public void setBorrow_nid(String borrow_nid) {
        this.borrow_nid = borrow_nid;
    }

    public String getBorrow_period() {
        return borrow_period;
    }

    public void setBorrow_period(String borrow_period) {
        this.borrow_period = borrow_period;
    }

    public String getCount_down() {
        return count_down;
    }

    public void setCount_down(String count_down) {
        this.count_down = count_down;
    }

    public String getInvest_type() {
        return invest_type;
    }

    public void setInvest_type(String invest_type) {
        this.invest_type = invest_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTender_account_min() {
        return tender_account_min;
    }

    public void setTender_account_min(String tender_account_min) {
        this.tender_account_min = tender_account_min;
    }

    public String getTender_time() {
        return tender_time;
    }

    public void setTender_time(String tender_time) {
        this.tender_time = tender_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrow_type() {
        return borrow_type;
    }

    public void setBorrow_type(String borrow_type) {
        this.borrow_type = borrow_type;
    }

    public String getBorrow_account_wait() {
        return borrow_account_wait;
    }

    public void setBorrow_account_wait(String borrow_account_wait) {
        this.borrow_account_wait = borrow_account_wait;
    }

    public String getBorrow_end_time() {
        return borrow_end_time;
    }

    public void setBorrow_end_time(String borrow_end_time) {
        this.borrow_end_time = borrow_end_time;
    }

    public long getNow_time() {
        return now_time;
    }

    public void setNow_time(long now_time) {
        this.now_time = now_time;
    }

    public int get_borrow_end_time() {
        return _borrow_end_time;
    }

    public void set_borrow_end_time(int _borrow_end_time) {
        this._borrow_end_time = _borrow_end_time;
    }

    public String getBorrow_period_name() {
        return borrow_period_name;
    }

    public void setBorrow_period_name(String borrow_period_name) {
        this.borrow_period_name = borrow_period_name;
    }

    public String getBorrow_status_nid() {
        return borrow_status_nid;
    }

    public void setBorrow_status_nid(String borrow_status_nid) {
        this.borrow_status_nid = borrow_status_nid;
    }
}
