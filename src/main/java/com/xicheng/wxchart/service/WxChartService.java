package com.xicheng.wxchart.service;

import com.alibaba.fastjson.JSON;
import com.xicheng.wxchart.VO.*;
import com.xicheng.wxchart.util.HttpClientRes;
import com.xicheng.wxchart.util.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxChartService {
    @Autowired
    private RobotService robotService;

    public String getQcCode(HttpSession session) throws Exception {
        session.setAttribute(WxChartConst.COOKIE_VALUE, "mm_lang=zh_CN;");
        String url = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage&fun=new&lang=zh_CN&_=1573629278255";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie", "mm_lang=zh_CN");
        HttpClientRes httpClientRes = HttpClientUtil.doGet(url, header, null);
        String content = httpClientRes.getContent();
        String qcCodeUrl = content.split("\"")[1];
        return qcCodeUrl;
    }


    public String isLogin(String qcCodeEndUrl) throws Exception {
        String isLoginUrl = "https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + qcCodeEndUrl + "&tip=0&r=-1670748235&_=157362863";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie", "mm_lang=zh_CN");
        HttpClientRes httpClientRes = HttpClientUtil.doGet(isLoginUrl, header, null);
        String content = httpClientRes.getContent();
        return content;
    }


    public void excute(String loginEndUrl, HttpSession session) throws Exception {
        session.setAttribute(WxChartConst.RES, new WebSyncRes());
        session.setAttribute(WxChartConst.ROBOT_RESULT_USERNAMELIST, new ArrayList<>());
        String newcookie = ((String) session.getAttribute(WxChartConst.COOKIE_VALUE)) + "MM_WX_NOTIFY_STATE=1;MM_WX_SOUND_STATE=1;";
        session.setAttribute(WxChartConst.COOKIE_VALUE, newcookie);
        String getSidAndSkeyUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?" + loginEndUrl + "&fun=new&version=v2&lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie", "mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1");
        HttpClientRes httpClientRes = HttpClientUtil.doGet(getSidAndSkeyUrl, header, null);
        String content = httpClientRes.getContent();
        System.out.println(content);
        Header[] setCookies = httpClientRes.getSetCookies();
        for (Header setCookie : setCookies) {
            HeaderElement[] elements = setCookie.getElements();
            String newC = ((String) session.getAttribute(WxChartConst.COOKIE_VALUE)) + elements[0].getName() + "=" + elements[0].getValue() + ";";
            session.setAttribute(WxChartConst.COOKIE_VALUE, newC);
        }
        System.out.println("最终的cookie是：" + ((String) session.getAttribute(WxChartConst.COOKIE_VALUE)));
        String skey = content.split("<skey>|</skey>")[1];
        String sid = content.split("<wxsid>|</wxsid>")[1];
        String uin = content.split("<wxuin>|</wxuin>")[1];

        //初始化
        wxinit(sid, skey, uin, session);
        //监听(每当有错误代码的时候抛出异常，防止死循环
        for (; ; ) {
            System.out.println("----监听中----");
            //syncCheck
            int status = 0;
            try {
                status = syncCheck(sid, skey, uin, session);
            } catch (ClientProtocolException e) {
                //status为finied的时候会报错，这里直接捕捉到
                continue;
            }
            if (status == 2) {
//                System.out.println("----有消息进来了----"+status);
                webSync(sid, skey, uin, session);
                //websync
                List<WebSyncMsgRes> addMsgList = ((WebSyncRes) session.getAttribute(WxChartConst.RES)).getAddMsgList();
                //从服务器发送的数据addMsgList为空，从其他的客户端发送不为空
                if (addMsgList != null && addMsgList.size() != 0) {
                    for (WebSyncMsgRes webSyncMsgRes : addMsgList) {
                        //只是行为记录数据
                        if ("".equals(webSyncMsgRes.getContent())) continue;

                        //包含自己发送的数据
                        System.out.println(webSyncMsgRes.getFromUserName() + "发送了一条信息给" + webSyncMsgRes.getToUserName());
                        //收到的信息
                        String from = webSyncMsgRes.getFromUserName();
                        boolean canAutoResult = false;
                        for (String robotResultUserName : (List<String>) session.getAttribute(WxChartConst.ROBOT_RESULT_USERNAMELIST)) {
                            if (from.equals(robotResultUserName)) {
                                canAutoResult = true;
                                break;
                            }
                        }
                        if ((!((String) session.getAttribute(WxChartConst.USERNAME)).equals(from)) && canAutoResult) {
                            if (webSyncMsgRes.getContent().split("type=\"revokemsg\"&gt;").length > 1) continue;
                            String msgTmp = "";
                            //群里的消息
                            if (from.startsWith("@@")) {
                                String s = webSyncMsgRes.getContent().split("<br/>")[1];
                                msgTmp = s.substring(0, s.length() - 1);

                            } else {
                                msgTmp = webSyncMsgRes.getContent();
                            }

                            String msg = msgTmp;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        robotSendMsg(msg, sid, skey, uin, webSyncMsgRes.getToUserName(), webSyncMsgRes.getFromUserName(), session);
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

    private void wxinit(String sid, String skey, String uin, HttpSession session) throws Exception {
        String wxinitUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=-1674625016&lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie", ((String) session.getAttribute(WxChartConst.COOKIE_VALUE)));
        header.put("Content-Type", "application/json");
        HashMap<String, Map<String, String>> requestbody = new HashMap<>();
        HashMap<String, String> basequest = new HashMap<>();
        basequest.put("Uin", uin);
        basequest.put("Sid", sid);
        basequest.put("Skey", "");
        basequest.put("DeviceID", "e525180873419679");

        requestbody.put("BaseRequest", basequest);
        System.out.println(JSON.toJSONString(requestbody));
        HttpClientRes httpClientRes = HttpClientUtil.doPost(wxinitUrl, header, JSON.toJSONString(requestbody));
        String content = httpClientRes.getContent();
//        System.out.println(content);
        ContentRes contentRes = JSON.parseObject(content, ContentRes.class);
        session.setAttribute(WxChartConst.CONTACTLIST, contentRes.getContactList());
        session.setAttribute(WxChartConst.USERNAME, contentRes.getUser().getUserName());
        System.out.println("username是" + contentRes.getUser().getUserName());
        ((WebSyncRes) session.getAttribute(WxChartConst.RES)).setSyncCheckKey(contentRes.getSynckey());
    }

    public int syncCheck(String sid, String skey, String uin, HttpSession session) throws Exception {

        List<SyncKV> syncKVS = ((WebSyncRes) session.getAttribute(WxChartConst.RES)).getSyncCheckKey().getList();
        StringBuffer sb = new StringBuffer();
        for (SyncKV syncKV : syncKVS) {
            sb.append("|" + syncKV.getKey() + "_" + syncKV.getVal());
        }
        String synckey = sb.toString().substring(1);
        String syncCheckUrl = "https://webpush.wx.qq.com/cgi-bin/mmwebwx-bin/synccheck?r=1573884084398";
        HashMap<String, String> params = new HashMap<>();
        params.put("skey", skey);
        params.put("sid", sid);
        params.put("uin", uin);
        params.put("deviceid", "e122876528850705");
        params.put("synckey", synckey);
        params.put("_", "1573884077841");
        HashMap<String, String> header = new HashMap<>();
        System.out.println(JSON.toJSONString(params));
        header.put("Cookie", ((String) session.getAttribute(WxChartConst.COOKIE_VALUE)));
        HttpClientRes httpClientRes = HttpClientUtil.doGet(syncCheckUrl, header, params);
        String content = httpClientRes.getContent();
        System.out.println(content);
        int retcode = Integer.parseInt(content.split("\"")[1]);
        String status = content.split("\"")[3];
        if (retcode != 0 || status.equals("3")) {
            System.out.println("已经注销登陆，线程结束结束！");
            session.setAttribute(WxChartConst.CONTACTLIST, null);
            session.setAttribute(WxChartConst.ROBOT_RESULT_USERNAMELIST, null);
            session.setAttribute(WxChartConst.COOKIE_VALUE, null);
            session.setAttribute(WxChartConst.USERNAME, null);
            session.setAttribute(WxChartConst.RES, null);
            Thread.currentThread().stop();
        }

        return Integer.parseInt(status);


    }

    public void webSync(String sid, String skey, String uin, HttpSession session) throws Exception {
        String webSyncUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid=" + sid + "&skey=" + skey + "&lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie", ((String) session.getAttribute(WxChartConst.COOKIE_VALUE)));
        header.put("Content-Type", "application/json");
        HashMap<String, Object> requestbody = new HashMap<>();
        HashMap<String, String> basequest = new HashMap<>();
        basequest.put("Uin", uin);
        basequest.put("Sid", sid);
        basequest.put("Skey", skey);
        basequest.put("DeviceID", "e525180873419679");
        requestbody.put("BaseRequest", basequest);
        requestbody.put("SyncKey", ((WebSyncRes) session.getAttribute(WxChartConst.RES)).getSyncCheckKey());
//        System.out.println(JSON.toJSONString(requestbody));
        HttpClientRes httpClientRes = HttpClientUtil.doPost(webSyncUrl, header, JSON.toJSONString(requestbody));
        String content = httpClientRes.getContent();

//        System.out.println(content);
        session.setAttribute(WxChartConst.RES, JSON.parseObject(content, WebSyncRes.class));


    }

    public void robotSendMsg(String msg, String sid, String skey, String uin, String from, String to, HttpSession session) throws Exception {
        RobotRes robotRes = robotService.doSendMsg(msg);
        String sendMsgUrl = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?lang=zh_CN";
        HashMap<String, String> header = new HashMap<>();
        header.put("Cookie", ((String) session.getAttribute(WxChartConst.COOKIE_VALUE)));
        header.put("Content-Type", "application/json");
        HashMap<String, Object> requestbody = new HashMap<>();
        HashMap<String, String> basequest = new HashMap<>();
        HashMap<String, Object> information = new HashMap<>();
        basequest.put("Uin", uin);
        basequest.put("Sid", sid);
        basequest.put("Skey", skey);
        basequest.put("DeviceID", "e525180873419679");
        information.put("ClientMsgId", String.valueOf(System.currentTimeMillis()));
        information.put("Content", robotRes.getResults().get(0).getValues().getText());
        information.put("FromUserName", from);
        information.put("ToUserName", to);
        information.put("LocalID", String.valueOf(System.currentTimeMillis()));
        information.put("Type", 1);

        requestbody.put("BaseRequest", basequest);
        requestbody.put("Msg", information);
        requestbody.put("Scene", 0);
        System.out.println(JSON.toJSONString(requestbody));
        HttpClientRes httpClientRes = HttpClientUtil.doPost(sendMsgUrl, header, JSON.toJSONString(requestbody));
        System.out.println(httpClientRes.getContent());

    }


}
