package com.xicheng.wxchart.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.xicheng.wxchart.VO.*;
import com.xicheng.wxchart.util.HttpClientRes;
import com.xicheng.wxchart.util.HttpClientUtil;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxChartService {

    private WebSyncRes res = new WebSyncRes();

    //登陆的人的username
    private String userName;

    private List<Contact> contactList;

    public List<Contact> getContactList() {
        return contactList;
    }

    //机器人自动回复的username
    private List<String> robotResultUserNameList = new ArrayList<>();


    public WebSyncRes getRes() {
        return res;
    }

    public void setRes(WebSyncRes res) {
        this.res = res;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public List<String> getRobotResultUserNameList() {
        return robotResultUserNameList;
    }

    public void setRobotResultUserNameList(List<String> robotResultUserNameList) {
        this.robotResultUserNameList = robotResultUserNameList;
    }

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
        System.out.println(content);
        String skey = content.split("<skey>|</skey>")[1];
        String sid = content.split("<wxsid>|</wxsid>")[1];
        String uin = content.split("<wxuin>|</wxuin>")[1];

        //初始化
        wxinit(sid, skey, uin);
        //监听(每当有错误代码的时候抛出异常，防止死循环
        for(;;){
            System.out.println("----监听中----");
                //syncCheck
            int status = 0;
            try {
                status = syncCheck(sid, skey, uin);
            }catch (ClientProtocolException e){
                //status为finied的时候会报错，这里直接捕捉到
                continue;
            }
            if(status==2){
//                System.out.println("----有消息进来了----"+status);
                webSync(sid,skey,uin);
                //websync
                List<WebSyncMsgRes> addMsgList = res.getAddMsgList();
                //从服务器发送的数据addMsgList为空，从其他的客户端发送不为空
                if(addMsgList!=null && addMsgList.size()!=0){
                    for (WebSyncMsgRes webSyncMsgRes : addMsgList) {
                        //只是行为记录数据
                        if("".equals(webSyncMsgRes.getContent()))continue;

                        //包含自己发送的数据
                        System.out.println(webSyncMsgRes.getFromUserName()+"发送了一条信息给"+webSyncMsgRes.getToUserName());
                        //收到的信息
                        String from =  webSyncMsgRes.getFromUserName();
                        boolean canAutoResult = false;
                        for (String robotResultUserName : robotResultUserNameList) {
                            if(from.equals(robotResultUserName)){
                                canAutoResult = true;
                                break;
                            }
                        }
                        if((!userName.equals(from))&&canAutoResult){
                            String msg = webSyncMsgRes.getContent();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        robotSendMsg(msg,sid,skey,uin,webSyncMsgRes.getToUserName(),webSyncMsgRes.getFromUserName());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }
                }

            }

        }

    }

    private void wxinit(String sid, String skey,String uin) throws Exception {
        String wxinitUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=-1674625016&lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie","pgv_pvid=6183515156; pgv_pvi=3814835200; RK=155lugMJWL; ptcz=59a0715d892f1b646e0c7dc445ddacd28fd47ce3594971683990db1e352e30c6; tvfe_boss_uuid=433d039493259004; webwxuvid=02b485cb75d54a02af2b3d8a2f0f63c280981906ed468ff1f827b6d54a1124a8dd0cfc06d2d27bc27cbc1fd6adc6508b; eas_sid=g1E536z3p5N9x3P0U2H8m683B7; uin_cookie=o1836810134; ied_qq=o1836810134; o_cookie=1836810134; pgv_si=s2394191872; ptisp=cnc; ptui_loginuin=1835901302@qq.com; uin=o1835901302; skey=@dxaPWH9bH; mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; wxuin=1020632462; last_wxuin=1020632462; refreshTimes=2; wxpluginkey=1573880521; wxsid=rLX3bc9va7sL+Pe3; webwx_data_ticket=gScnwqTKQrt5qgfBYb8u81Gq; webwx_auth_ticket=CIsBEPu3s6MFGoABXzdbKqxXilP+tPFE7iGcIF8wQedEvrdSndRbxZQPY2mRAp97iKevsLhy9utjaqwSEJxrP3iLFGd/Ii4GwV0BR7gxVOqSMqETDSv+JZjRmIRyhop0Ksbaty4TkqxSYsaPu73ombIvjEaprhhsL228BtBjrzzU8kW79duXK+T2BGc=; login_frequency=1; wxloadtime=1573882861_expired");
        header.put("Content-Type","application/json");
        HashMap<String, Map<String,String>> requestbody = new HashMap<>();
        HashMap<String, String> basequest = new HashMap<>();
        basequest.put("Uin",uin);
        basequest.put("Sid",sid);
        basequest.put("Skey","");
        basequest.put("DeviceID","e525180873419679");

        requestbody.put("BaseRequest", basequest);
        System.out.println(JSON.toJSONString(requestbody));
        HttpClientRes httpClientRes = HttpClientUtil.doPost(wxinitUrl, header, JSON.toJSONString(requestbody));
        String content = httpClientRes.getContent();
//        System.out.println(content);
        ContentRes contentRes = JSON.parseObject(content, ContentRes.class);
        contactList = contentRes.getContactList();
        userName = contentRes.getUser().getUserName();
        System.out.println("username是"+userName);
        res.setSyncCheckKey(contentRes.getSynckey());
    }

    public int syncCheck(String sid,String skey,String uin) throws Exception {

        List<SyncKV> syncKVS = res.getSyncCheckKey().getList();
        StringBuffer sb = new StringBuffer();
        for (SyncKV syncKV : syncKVS) {
            sb.append("|"+syncKV.getKey()+"_"+syncKV.getVal());
        }
        String synckey = sb.toString().substring(1);
        String syncCheckUrl = "https://webpush.wx.qq.com/cgi-bin/mmwebwx-bin/synccheck?r=1573884084398";
        HashMap<String, String> params = new HashMap<>();
        params.put("skey",skey);
        params.put("sid",sid);
        params.put("uin",uin);
        params.put("deviceid","e122876528850705");
        params.put("synckey",synckey);
        params.put("_","1573884077841");
        HashMap<String, String> header = new HashMap<>();
        System.out.println(JSON.toJSONString(params));
        header.put("Cookie","pgv_pvid=6183515156; pgv_pvi=3814835200; RK=155lugMJWL; ptcz=59a0715d892f1b646e0c7dc445ddacd28fd47ce3594971683990db1e352e30c6; tvfe_boss_uuid=433d039493259004; webwxuvid=02b485cb75d54a02af2b3d8a2f0f63c280981906ed468ff1f827b6d54a1124a8dd0cfc06d2d27bc27cbc1fd6adc6508b; eas_sid=g1E536z3p5N9x3P0U2H8m683B7; uin_cookie=o1836810134; ied_qq=o1836810134; o_cookie=1836810134; pgv_si=s2394191872; ptisp=cnc; ptui_loginuin=1835901302@qq.com; uin=o1835901302; skey=@dxaPWH9bH; mm_lang=zh_CN; wxuin=1020632462; wxpluginkey=1573880521; wxsid=TJwgQ8//n6owhqhe; wxloadtime=1573884083; webwx_data_ticket=gScXl7xcC/ED0kay0/OZ8Xhn; webwx_auth_ticket=CIsBEKa3n4cDGoABmsQZSQvMbfNyCfslxToeqV8wQedEvrdSndRbxZQPY2mRAp97iKevsLhy9utjaqwSEJxrP3iLFGd/Ii4GwV0BR7gxVOqSMqETDSv+JZjRmIRyhop0Ksbaty4TkqxSYsaPu73ombIvjEaprhhsL228BtBjrzzU8kW79duXK+T2BGc=");
        HttpClientRes httpClientRes = HttpClientUtil.doGet(syncCheckUrl, header, params);
        String content = httpClientRes.getContent();
        System.out.println(content);
        String status = content.split("\"")[3];
        return Integer.parseInt(status);


    }

    public void webSync(String sid,String skey,String uin) throws Exception {
        String webSyncUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid="+sid+"&skey="+skey+"&lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie","pgv_pvid=6183515156; pgv_pvi=3814835200; RK=155lugMJWL; ptcz=59a0715d892f1b646e0c7dc445ddacd28fd47ce3594971683990db1e352e30c6; tvfe_boss_uuid=433d039493259004; webwxuvid=02b485cb75d54a02af2b3d8a2f0f63c280981906ed468ff1f827b6d54a1124a8dd0cfc06d2d27bc27cbc1fd6adc6508b; eas_sid=g1E536z3p5N9x3P0U2H8m683B7; uin_cookie=o1836810134; ied_qq=o1836810134; o_cookie=1836810134; pgv_si=s2394191872; ptisp=cnc; ptui_loginuin=1835901302@qq.com; uin=o1835901302; skey=@dxaPWH9bH; mm_lang=zh_CN; wxuin=1020632462; wxpluginkey=1573880521; wxsid=TJwgQ8//n6owhqhe; wxloadtime=1573884083; webwx_data_ticket=gScXl7xcC/ED0kay0/OZ8Xhn; webwx_auth_ticket=CIsBEKa3n4cDGoABmsQZSQvMbfNyCfslxToeqV8wQedEvrdSndRbxZQPY2mRAp97iKevsLhy9utjaqwSEJxrP3iLFGd/Ii4GwV0BR7gxVOqSMqETDSv+JZjRmIRyhop0Ksbaty4TkqxSYsaPu73ombIvjEaprhhsL228BtBjrzzU8kW79duXK+T2BGc=");
        header.put("Content-Type","application/json");
        HashMap<String, Object> requestbody = new HashMap<>();
        HashMap<String, String> basequest = new HashMap<>();
        basequest.put("Uin",uin);
        basequest.put("Sid",sid);
        basequest.put("Skey",skey);
        basequest.put("DeviceID","e525180873419679");
        requestbody.put("BaseRequest",basequest);
        requestbody.put("SyncKey",res.getSyncCheckKey());
//        System.out.println(JSON.toJSONString(requestbody));
        HttpClientRes httpClientRes = HttpClientUtil.doPost(webSyncUrl, header, JSON.toJSONString(requestbody));
        String content = httpClientRes.getContent();

//        System.out.println(content);
        res = JSON.parseObject(content,WebSyncRes.class);



    }

    public void robotSendMsg(String msg,String sid,String skey,String uin,String from,String to) throws Exception {
        String robotUrl = "http://openapi.tuling123.com/openapi/api/v2";
        HashMap<String, String> robotHeader = new HashMap<>();
        robotHeader.put("Content-Type","application/json");
        String requestJson = "{\n" +
                "\t\"reqType\":0,\n" +
                "    \"perception\": {\n" +
                "        \"inputText\": {\n" +
                "            \"text\": \""+msg+"\"\n" +
                "        }\n" +
                "       \n" +
                "    },\n" +
                "    \"userInfo\": {\n" +
                "        \"apiKey\": \"d9a9525ca9cf4534af49a460dfc63d74\",\n" +
                "        \"userId\": \"23\"\n" +
                "    }\n" +
                "}";
        System.out.println(requestJson);
        HttpClientRes httpClientRobotRes = HttpClientUtil.doPost(robotUrl, robotHeader, requestJson);
        System.out.println(httpClientRobotRes.getContent());
        RobotRes robotRes = JSON.parseObject(httpClientRobotRes.getContent(), RobotRes.class);


//        System.out.println(robotRes.getResults().get(0).getValues().getText());
        String sendMsgUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie","pgv_pvid=6183515156; pgv_pvi=3814835200; RK=155lugMJWL; ptcz=59a0715d892f1b646e0c7dc445ddacd28fd47ce3594971683990db1e352e30c6; tvfe_boss_uuid=433d039493259004; webwxuvid=02b485cb75d54a02af2b3d8a2f0f63c280981906ed468ff1f827b6d54a1124a8dd0cfc06d2d27bc27cbc1fd6adc6508b; eas_sid=g1E536z3p5N9x3P0U2H8m683B7; uin_cookie=o1836810134; ied_qq=o1836810134; o_cookie=1836810134; pgv_si=s2394191872; ptisp=cnc; ptui_loginuin=1835901302@qq.com; uin=o1835901302; skey=@dxaPWH9bH; mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; wxuin=1020632462; last_wxuin=1020632462; refreshTimes=5; wxpluginkey=1573909681; wxsid=H62ADFZFxWUohbPF; webwx_data_ticket=gSdK3Uuz6VBfR3dNKP4SK4Ny; webwx_auth_ticket=CIsBEJq4/4MGGoABiXED/QuXe1Qdf3ekwYXigl8wQedEvrdSndRbxZQPY2mRAp97iKevsLhy9utjaqwSEJxrP3iLFGd/Ii4GwV0BR7gxVOqSMqETDSv+JZjRmIRyhop0Ksbaty4TkqxSYsaPu73ombIvjEaprhhsL228BtBjrzzU8kW79duXK+T2BGc=; login_frequency=2; wxloadtime=1573917105_expired");
        header.put("Content-Type","application/json");
        HashMap<String, Object> requestbody = new HashMap<>();
        HashMap<String, String> basequest = new HashMap<>();
        HashMap<String, Object> information = new HashMap<>();
        basequest.put("Uin",uin);
        basequest.put("Sid",sid);
        basequest.put("Skey",skey);
        basequest.put("DeviceID","e525180873419679");
        information.put("ClientMsgId",String.valueOf(System.currentTimeMillis()));
        information.put("Content", robotRes.getResults().get(0).getValues().getText());
        information.put("FromUserName",from);
        information.put("ToUserName",to);
        information.put("LocalID",String.valueOf(System.currentTimeMillis()));
        information.put("Type",1);

        requestbody.put("BaseRequest",basequest);
        requestbody.put("Msg",information);
        requestbody.put("Scene",0);
        System.out.println(JSON.toJSONString(requestbody));
        HttpClientRes httpClientRes = HttpClientUtil.doPost(sendMsgUrl, header, JSON.toJSONString(requestbody));
        System.out.println(httpClientRes.getContent());

    }





}
