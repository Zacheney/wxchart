package com.xicheng.wxchart.util;

import java.io.Serializable;

public class HttpClientRes implements Serializable {


    private int code;

    private String content;

    public HttpClientRes(int code) {
        this.code = code;
    }

    public HttpClientRes(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HttpClientRes{" +
                "code=" + code +
                ", content='" + content + '\'' +
                '}';
    }
}
