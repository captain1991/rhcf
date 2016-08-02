package com.ayd.rhcf.bean;

import java.io.Serializable;

/**
 * Created by yxd on 2016/5/23.
 */
public class User implements Serializable {

    /**
     * paypsw_status : 0
     * bank_status : 6225***8751
     * Capital_total : 213534.92
     * Capital_balance : 212188.38
     * Capital_frost : -376.00
     * recover_yes_profit : 1218.370000
     * tender_interest_yes : 0
     * recover_yes_interest : 0
     * recover_wait_interest : 0
     * username : xcom2
     * vip_status : 1
     * avatar_url :
     * email_status : 0
     * phone_status : 1
     * phone : 15212412121
     * realname_status : 1
     * realname : 顾岚彩
     * _card_id : 5117****7904
     * card_id : 511702198304257904
     * "trust": 1 是否开通了托管 0没开
     */

    private int paypsw_status;
    private String bank_status;
    private double Capital_total;
    private double Capital_balance;
    private String Capital_frost;
    private String recover_yes_profit;
    private float tender_interest_yes;
    private float recover_yes_interest;
    private float recover_wait_interest;
    private String username;
    private int vip_status;
    private String avatar_url;
    private int email_status;
    private int phone_status;
    private String phone;
    private int realname_status;
    private String realname;
    private String _card_id;
    private String card_id;
    private int trust;

    public void setTrust(int trust) {
        this.trust = trust;
    }

    public int getTrust() {

        return trust;
    }

    public int getPaypsw_status() {
        return paypsw_status;
    }

    public void setPaypsw_status(int paypsw_status) {
        this.paypsw_status = paypsw_status;
    }

    public String getBank_status() {
        return bank_status;
    }

    public void setBank_status(String bank_status) {
        this.bank_status = bank_status;
    }

    public double getCapital_total() {
        return Capital_total;
    }

    public void setCapital_total(double Capital_total) {
        this.Capital_total = Capital_total;
    }

    public double getCapital_balance() {
        return Capital_balance;
    }

    public void setCapital_balance(double Capital_balance) {
        this.Capital_balance = Capital_balance;
    }

    public String getCapital_frost() {
        return Capital_frost;
    }

    public void setCapital_frost(String Capital_frost) {
        this.Capital_frost = Capital_frost;
    }

    public String getRecover_yes_profit() {
        return recover_yes_profit;
    }

    public void setRecover_yes_profit(String recover_yes_profit) {
        this.recover_yes_profit = recover_yes_profit;
    }

    public float getTender_interest_yes() {
        return tender_interest_yes;
    }

    public void setTender_interest_yes(float tender_interest_yes) {
        this.tender_interest_yes = tender_interest_yes;
    }

    public float getRecover_yes_interest() {
        return recover_yes_interest;
    }

    public void setRecover_yes_interest(float recover_yes_interest) {
        this.recover_yes_interest = recover_yes_interest;
    }

    public float getRecover_wait_interest() {
        return recover_wait_interest;
    }

    public void setRecover_wait_interest(float recover_wait_interest) {
        this.recover_wait_interest = recover_wait_interest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVip_status() {
        return vip_status;
    }

    public void setVip_status(int vip_status) {
        this.vip_status = vip_status;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getEmail_status() {
        return email_status;
    }

    public void setEmail_status(int email_status) {
        this.email_status = email_status;
    }

    public int getPhone_status() {
        return phone_status;
    }

    public void setPhone_status(int phone_status) {
        this.phone_status = phone_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRealname_status() {
        return realname_status;
    }

    public void setRealname_status(int realname_status) {
        this.realname_status = realname_status;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String get_card_id() {
        return _card_id;
    }

    public void set_card_id(String _card_id) {
        this._card_id = _card_id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }
}
