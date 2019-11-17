package com.xicheng.wxchart.VO;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class WebSyncRes {

    @JSONField(name = "AddMsgList")
    private List<WebSyncMsgRes> AddMsgList;

    @JSONField(name = "SyncCheckKey")
    private Synckey SyncCheckKey;

    public List<WebSyncMsgRes> getAddMsgList() {
        return AddMsgList;
    }

    public void setAddMsgList(List<WebSyncMsgRes> addMsgList) {
        AddMsgList = addMsgList;
    }

    public Synckey getSyncCheckKey() {
        return SyncCheckKey;
    }

    public void setSyncCheckKey(Synckey syncCheckKey) {
        SyncCheckKey = syncCheckKey;
    }
}
