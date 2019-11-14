package com.xicheng.wxchart.VO;

import java.util.List;

public class Synckey {

    private  Integer Count;

    private List<SyncKV> syncKVS;


    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public List<SyncKV> getSyncKVS() {
        return syncKVS;
    }

    public void setSyncKVS(List<SyncKV> syncKVS) {
        this.syncKVS = syncKVS;
    }
}
