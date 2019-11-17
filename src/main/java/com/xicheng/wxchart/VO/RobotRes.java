package com.xicheng.wxchart.VO;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class RobotRes {

    @JSONField(name = "results")
    private List<RobotContentRes> results;

    public List<RobotContentRes> getResults() {
        return results;
    }

    public void setResults(List<RobotContentRes> results) {
        this.results = results;
    }
}
