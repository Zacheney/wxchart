package com.xicheng.wxchart.service;

import com.alibaba.fastjson.JSON;
import com.xicheng.wxchart.util.HttpClientRes;
import com.xicheng.wxchart.util.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class WxChartService {


    public String getQcCode() throws Exception {
        String url = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage&fun=new&lang=zh_CN&_=1573629278255";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie","pgv_pvid=306419002; pgv_pvi=6773444608; RK=sw5lviMBEL; ptcz=1ffcd7fe91dec152cdff8657f6a8f4143c18f40c7209f92b3c836fdb80c33a05; pac_uid=1_1836810134; eas_sid=j1J5y45855m1k3x3P3q8Y1R3v0; webwxuvid=ea91688edb313b3eb817f24de9b4b2647cac46127639cc2a772b8febc66b8bc246de4c221750e662673a0d33c9abde8b; _ga=GA1.2.746271150.1558590289; ied_qq=o1836810134; tvfe_boss_uuid=d2c01d22a47008d3; o_cookie=1835901302; luin=o1836810134; lskey=0001000094d493a4b8af70c1c92ae045d7e6374683348a7073e2d61bdce69dc09abc34ce7917e1607f1ea1c4; ptui_loginuin=1836810134@qq.com; wxuin=1020632462; pgv_info=ssid=s2293734787; wdqyqqcomrouteLine=a20190814downloadpc_index_newsdetail_index_a20190814downloadpc_a20190814downloadpc; mm_lang=zh_CN; webwx_auth_ticket=CIsBEPGzwxwagAESLyzAV5PtYG8WDB7wjxpQqk7aV+oIOD8iwVbUaLOz0OO5Y3U0knGA++cKpyS+aw1eyqCMASOXAC2Qq6mUvVhOAwDH3OIiDaKAxoiJ2wTzGuoGFpVb0M8ZstFuALBrONNLqdZmvpff/aphM0FWiq9di0drxquFNzI0YMmYKSTgEA==; wxloadtime=1573741276_expired; wxpluginkey=1573740005");
        HttpClientRes httpClientRes = HttpClientUtil.doGet(url, header, null);
        String content = httpClientRes.getContent();
        String qcCodeUrl = content.split("\"")[1];
        return qcCodeUrl;
    }


    public String isLogin(String qcCodeEndUrl) throws Exception {
        String isLoginUrl = "https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid="+qcCodeEndUrl+"&tip=0&r=-1670748235&_=157362863";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie","pgv_pvid=306419002; pgv_pvi=6773444608; RK=sw5lviMBEL; ptcz=1ffcd7fe91dec152cdff8657f6a8f4143c18f40c7209f92b3c836fdb80c33a05; pac_uid=1_1836810134; eas_sid=j1J5y45855m1k3x3P3q8Y1R3v0; webwxuvid=ea91688edb313b3eb817f24de9b4b2647cac46127639cc2a772b8febc66b8bc246de4c221750e662673a0d33c9abde8b; _ga=GA1.2.746271150.1558590289; ied_qq=o1836810134; tvfe_boss_uuid=d2c01d22a47008d3; o_cookie=1835901302; luin=o1836810134; lskey=0001000094d493a4b8af70c1c92ae045d7e6374683348a7073e2d61bdce69dc09abc34ce7917e1607f1ea1c4; ptui_loginuin=1836810134@qq.com; wxuin=1020632462; pgv_info=ssid=s2293734787; wdqyqqcomrouteLine=a20190814downloadpc_index_newsdetail_index_a20190814downloadpc_a20190814downloadpc; mm_lang=zh_CN; webwx_auth_ticket=CIsBEPGzwxwagAESLyzAV5PtYG8WDB7wjxpQqk7aV+oIOD8iwVbUaLOz0OO5Y3U0knGA++cKpyS+aw1eyqCMASOXAC2Qq6mUvVhOAwDH3OIiDaKAxoiJ2wTzGuoGFpVb0M8ZstFuALBrONNLqdZmvpff/aphM0FWiq9di0drxquFNzI0YMmYKSTgEA==; wxloadtime=1573741276_expired; wxpluginkey=1573740005");
        HttpClientRes httpClientRes = HttpClientUtil.doGet(isLoginUrl, header, null);
        String content = httpClientRes.getContent();
        return content;
    }


    public void excute(String loginEndUrl) throws Exception {
        String getSidAndSkeyUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?"+loginEndUrl+"&fun=new&version=v2&lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie","pgv_pvid=306419002; pgv_pvi=6773444608; RK=sw5lviMBEL; ptcz=1ffcd7fe91dec152cdff8657f6a8f4143c18f40c7209f92b3c836fdb80c33a05; pac_uid=1_1836810134; eas_sid=j1J5y45855m1k3x3P3q8Y1R3v0; webwxuvid=ea91688edb313b3eb817f24de9b4b2647cac46127639cc2a772b8febc66b8bc246de4c221750e662673a0d33c9abde8b; _ga=GA1.2.746271150.1558590289; ied_qq=o1836810134; tvfe_boss_uuid=d2c01d22a47008d3; o_cookie=1835901302; luin=o1836810134; lskey=0001000094d493a4b8af70c1c92ae045d7e6374683348a7073e2d61bdce69dc09abc34ce7917e1607f1ea1c4; ptui_loginuin=1836810134@qq.com; wxuin=1020632462; pgv_info=ssid=s2293734787; wdqyqqcomrouteLine=a20190814downloadpc_index_newsdetail_index_a20190814downloadpc_a20190814downloadpc; mm_lang=zh_CN; webwx_auth_ticket=CIsBEPGzwxwagAESLyzAV5PtYG8WDB7wjxpQqk7aV+oIOD8iwVbUaLOz0OO5Y3U0knGA++cKpyS+aw1eyqCMASOXAC2Qq6mUvVhOAwDH3OIiDaKAxoiJ2wTzGuoGFpVb0M8ZstFuALBrONNLqdZmvpff/aphM0FWiq9di0drxquFNzI0YMmYKSTgEA==; wxloadtime=1573741276_expired; wxpluginkey=1573740005");
        HttpClientRes httpClientRes = HttpClientUtil.doGet(getSidAndSkeyUrl, header, null);
        String content = httpClientRes.getContent();
        String skey = content.split("<skey>|</skey>")[1];
        String sid = content.split("<wxsid>|</wxsid>")[1];
        String uin = content.split("<wxuin>|</wxuin>")[1];

        wxinit(sid,skey,uin);
//        System.out.println(skey);
//        System.out.println(sid);
    }

    private void wxinit(String sid, String skey,String uin) throws Exception {
        String wxinitUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=-1674625016&lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie","pgv_pvid=306419002; pgv_pvi=6773444608; RK=sw5lviMBEL; ptcz=1ffcd7fe91dec152cdff8657f6a8f4143c18f40c7209f92b3c836fdb80c33a05; pac_uid=1_1836810134; eas_sid=j1J5y45855m1k3x3P3q8Y1R3v0; webwxuvid=ea91688edb313b3eb817f24de9b4b2647cac46127639cc2a772b8febc66b8bc246de4c221750e662673a0d33c9abde8b; _ga=GA1.2.746271150.1558590289; ied_qq=o1836810134; tvfe_boss_uuid=d2c01d22a47008d3; o_cookie=1835901302; luin=o1836810134; lskey=0001000094d493a4b8af70c1c92ae045d7e6374683348a7073e2d61bdce69dc09abc34ce7917e1607f1ea1c4; ptui_loginuin=1836810134@qq.com; wxuin=1020632462; pgv_info=ssid=s2293734787; wdqyqqcomrouteLine=a20190814downloadpc_index_newsdetail_index_a20190814downloadpc_a20190814downloadpc; mm_lang=zh_CN; webwx_auth_ticket=CIsBEPGzwxwagAESLyzAV5PtYG8WDB7wjxpQqk7aV+oIOD8iwVbUaLOz0OO5Y3U0knGA++cKpyS+aw1eyqCMASOXAC2Qq6mUvVhOAwDH3OIiDaKAxoiJ2wTzGuoGFpVb0M8ZstFuALBrONNLqdZmvpff/aphM0FWiq9di0drxquFNzI0YMmYKSTgEA==; wxloadtime=1573741276_expired; wxpluginkey=1573740005");
        header.put("Content-Type","application/json");
        HashMap<String, String> requestbody = new HashMap<>();
        HashMap<String, String> basequest = new HashMap<>();
        basequest.put("Uin",uin);
        basequest.put("Sid",sid);
        basequest.put("Skey",skey);
        basequest.put("DeviceID","e525180873419679");

        requestbody.put("BaseRequest", JSON.toJSONString(basequest));
        HttpClientRes httpClientRes = HttpClientUtil.doPost(wxinitUrl, header, requestbody);
        String content = httpClientRes.getContent();
        System.out.println(content);

    }

}
