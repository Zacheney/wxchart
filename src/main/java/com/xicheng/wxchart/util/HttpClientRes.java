package com.xicheng.wxchart.util;

import org.apache.http.Header;

import java.io.Serializable;

public class HttpClientRes implements Serializable {


    private int code;

    private String content;

    private Header[] setCookies;

    public Header[] getSetCookies() {
        return setCookies;
    }

    public void setSetCookies(Header[] setCookies) {
        this.setCookies = setCookies;
    }

    public HttpClientRes(int code) {
        this.code = code;
    }

    public HttpClientRes(int code, String content, Header[] setCookies) {
        this.code = code;
        this.content = content;
        this.setCookies = setCookies;
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
