package com.ayd.rhcf.bean;

import java.io.Serializable;

/**
 * Created by yxd on 2016/7/22.
 */
public class Friend implements Serializable {

    /**
     * addip : 127.0.0.1
     * status : 1
     * vip_status : 0
     * type : 1
     * verify_time :
     * friends_userid : 432
     * content : null
     * id : 111
     * username : xtzceshi1
     * addtime : 1462864142
     * trust_invite_vip_get_account : 0
     * user_id : 345
     * credit : 0
     * iuser_id : 432
     */

    private String addip;
    private String status;
    private String vip_status;
    private String type;
    private String verify_time;
    private String friends_userid;
    private Object content;
    private String id;
    private String username;
    private String addtime;
    private int trust_invite_vip_get_account;
    private String user_id;
    private String credit;
    private String iuser_id;
    private float tender;
    private String addtime2;

    public void setAddtime2(String addtime2) {
        this.addtime2 = addtime2;
    }

    public String getAddtime2() {

        return addtime2;
    }

    public void setTender(float tender) {
        this.tender = tender;
    }

    public float getTender() {

        return tender;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVip_status() {
        return vip_status;
    }

    public void setVip_status(String vip_status) {
        this.vip_status = vip_status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerify_time() {
        return verify_time;
    }

    public void setVerify_time(String verify_time) {
        this.verify_time = verify_time;
    }

    public String getFriends_userid() {
        return friends_userid;
    }

    public void setFriends_userid(String friends_userid) {
        this.friends_userid = friends_userid;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getTrust_invite_vip_get_account() {
        return trust_invite_vip_get_account;
    }

    public void setTrust_invite_vip_get_account(int trust_invite_vip_get_account) {
        this.trust_invite_vip_get_account = trust_invite_vip_get_account;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getIuser_id() {
        return iuser_id;
    }

    public void setIuser_id(String iuser_id) {
        this.iuser_id = iuser_id;
    }
}
