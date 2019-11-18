package com.xicheng.wxchart.VO;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ContentRes {


    @JSONField(name = "Synckey")
    private Synckey Synckey;

    private User User;

    private List<Contact> ContactList;

    public List<Contact> getContactList() {
        return ContactList;
    }

    public void setContactList(List<Contact> contactList) {
        ContactList = contactList;
    }

    public com.xicheng.wxchart.VO.Synckey getSynckey() {
        return Synckey;
    }

    public void setSynckey(com.xicheng.wxchart.VO.Synckey synckey) {
        Synckey = synckey;
    }

    public com.xicheng.wxchart.VO.User getUser() {
        return User;
    }

    public void setUser(com.xicheng.wxchart.VO.User user) {
        User = user;
    }
}


