package com.ayd.rhcf.bean;

import java.io.Serializable;

/**
 * Created by yxd on 2016/7/21.
 */
public class HkjhBean implements Serializable {

    /**
     * account : 40
     * addtime : 2016/07/12
     * borrow_name : 10000
     * status_type_name : 回收中
     */

    private int account;
    private String addtime;
    private String borrow_name;
    private String status_type_name;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {

        return username;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getBorrow_name() {
        return borrow_name;
    }

    public void setBorrow_name(String borrow_name) {
        this.borrow_name = borrow_name;
    }

    public String getStatus_type_name() {
        return status_type_name;
    }

    public void setStatus_type_name(String status_type_name) {
        this.status_type_name = status_type_name;
    }
}
