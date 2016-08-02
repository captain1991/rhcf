package com.ayd.rhcf.bean;

import java.io.Serializable;

/**
 * Created by yxd on 2016/7/26.
 */
public class PtggBean implements Serializable {

    /**
     * id : 774
     * name : 【公告】融和贷问卷调查
     * typename : null
     * new_status : 0
     */

    private String id;
    private String name;
    private Object typename;
    private String new_status;

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

    public Object getTypename() {
        return typename;
    }

    public void setTypename(Object typename) {
        this.typename = typename;
    }

    public String getNew_status() {
        return new_status;
    }

    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }
}
