package com.ayd.rhcf.bean;

import java.io.Serializable;

/**
 * Created by yxd on 2016/7/25.
 * 交易记录
 */
public class JyjlBean implements Serializable {

    /**
     * type_name : 用于查看和管理平台所有用户/网站产生的每一条资金流和数据统计。具体包括：充值和提现、资金记录、用户与网站收支。
     * addtime : 2016-07-09 15:48
     * money : 15
     * balance : 212166.38
     */

    private String type_name;
    private String addtime;
    private double money;
    private double balance;
    private String remark;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {

        this.remark = remark;
    }
}
