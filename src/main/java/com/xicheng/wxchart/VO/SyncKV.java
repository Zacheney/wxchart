package com.xicheng.wxchart.VO;

import com.alibaba.fastjson.annotation.JSONField;

public class SyncKV {

    @JSONField(name = "Key")
    private  Integer Key;


    @JSONField(name = "Val")
    private  Integer Val;

    public Integer getKey() {
        return Key;
    }

    public void setKey(Integer key) {
        Key = key;
    }

    public Integer getVal() {
        return Val;
    }

    public void setVal(Integer val) {
        Val = val;
    }
}
