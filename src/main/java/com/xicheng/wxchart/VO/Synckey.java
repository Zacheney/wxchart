package com.xicheng.wxchart.VO;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Synckey {

    @JSONField(name = "Count")
    private  Integer Count;

    @JSONField(name = "List")
    private List<SyncKV> List;


    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public java.util.List<SyncKV> getList() {
        return List;
    }

    public void setList(java.util.List<SyncKV> list) {
        List = list;
    }
}
