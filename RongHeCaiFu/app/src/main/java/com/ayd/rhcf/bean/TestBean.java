package com.ayd.rhcf.bean;

import java.io.Serializable;

/**
 * 首页与列表页的标实体类
 * Created by gqy on 2016/3/12.
 */
public class TestBean implements Serializable {
    public int timeInMis = (0 * 24 + 24) * 3600 + 50;
    public int timeInMis1 = 20;
    public int progress;
    public String title;

    public long server_time;
    private String name;
    private String borrow_nid;
    private int status;
    private String view_type;
    private String account;
    private String borrow_end_time;
    private String borrow_account_yes;
    private String borrow_account_scale;
    private String borrow_style;
    private String borrow_period;
    private String borrow_apr;
    private String tender_account_min;
    private String tender_account_max;
    private String invest_type;
    private String count_down;
    private String partition;
    private String tender_time;
    private String block_status;
    private String status_name;

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getPartition() {
        return partition;
    }

    public long getServer_time() {
        return server_time;
    }

    public void setServer_time(long server_time) {
        this.server_time = server_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorrow_nid() {
        return borrow_nid;
    }

    public void setBorrow_nid(String borrow_nid) {
        this.borrow_nid = borrow_nid;
    }

    public String getView_type() {
        return view_type;
    }

    public void setView_type(String view_type) {
        this.view_type = view_type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBorrow_end_time() {
        return borrow_end_time;
    }

    public void setBorrow_end_time(String borrow_end_time) {
        this.borrow_end_time = borrow_end_time;
    }

    public String getBorrow_account_yes() {
        return borrow_account_yes;
    }

    public void setBorrow_account_yes(String borrow_account_yes) {
        this.borrow_account_yes = borrow_account_yes;
    }

    public String getBorrow_account_scale() {
        return borrow_account_scale;
    }

    public void setBorrow_account_scale(String borrow_account_scale) {
        this.borrow_account_scale = borrow_account_scale;
    }

    public String getBorrow_style() {
        return borrow_style;
    }

    public void setBorrow_style(String borrow_style) {
        this.borrow_style = borrow_style;
    }

    public String getBorrow_period() {
        return borrow_period;
    }

    public void setBorrow_period(String borrow_period) {
        this.borrow_period = borrow_period;
    }

    public String getBorrow_apr() {
        return borrow_apr;
    }

    public void setBorrow_apr(String borrow_apr) {
        this.borrow_apr = borrow_apr;
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

    public String getInvest_type() {
        return invest_type;
    }

    public void setInvest_type(String invest_type) {
        this.invest_type = invest_type;
    }

    public String getCount_down() {
        return count_down;
    }

    public void setCount_down(String count_down) {
        this.count_down = count_down;
    }

    public String getTender_time() {
        return tender_time;
    }

    public void setTender_time(String tender_time) {
        this.tender_time = tender_time;
    }

    public String getBlock_status() {
        return block_status;
    }

    public void setBlock_status(String block_status) {
        this.block_status = block_status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {

        return status;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {

        return progress;
    }

    public void setTimeInMis1(int timeInMis1) {
        this.timeInMis1 = timeInMis1;
    }

    public int getTimeInMis() {

        return timeInMis;
    }

    public int getTimeInMis1() {
        return timeInMis1;
    }

    public void setTimeInMis(int timeInMis) {

        this.timeInMis = timeInMis;
    }
}
