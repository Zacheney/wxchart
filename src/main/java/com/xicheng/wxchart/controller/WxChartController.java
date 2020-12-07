package com.xicheng.wxchart.controller;

import com.xicheng.wxchart.VO.Contact;
import com.xicheng.wxchart.service.WxChartConst;
import com.xicheng.wxchart.service.WxChartService;
import com.xicheng.wxchart.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@RestController
public class WxChartController {

    @Autowired
    private WxChartService wxChartService;

    @RequestMapping("wxLogin")
    public ResultVO wxChart(HttpSession session) throws Exception {
        //生成二维码地址
        String qcCodeEndUrl = wxChartService.getQcCode(session);
        return new ResultVO("200", qcCodeEndUrl);
    }


    @RequestMapping("isLogin")
    public ResultVO isLogin(String qcCodeEndUrl, HttpSession session) throws Exception {
        String content = wxChartService.isLogin(qcCodeEndUrl);
        System.out.println(content);
        String[] contents = content.split(";");
        String windowCode = contents[0];
        if (windowCode.endsWith("408")) {
            return new ResultVO("408", null);
        } else if (windowCode.endsWith("201")) {
            String imgData = contents[1].split("\'")[1];
            return new ResultVO("201", imgData);
        } else {
            String loginEndUrl = contents[1].split("\"")[1].substring(56);
            //开启一条新线程去开始
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        wxChartService.excute(loginEndUrl, session);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return new ResultVO("200", loginEndUrl);
        }
    }


    @RequestMapping("getContact")
    public ResultVO getContact(HttpSession session) {
        List<Contact> contactList = (List<Contact>) session.getAttribute(WxChartConst.CONTACTLIST);
        if (contactList != null && contactList.size() > 0) {
            return new ResultVO("200", contactList);
        }
        return new ResultVO("201", null);
    }


    @RequestMapping("setRobotUserName")
    public ResultVO setRobotUserName(@RequestBody String[] robotUserName, HttpSession session) {
        if (robotUserName != null && robotUserName.length >= 0) {
            session.setAttribute(WxChartConst.ROBOT_RESULT_USERNAMELIST, Arrays.asList(robotUserName));
            System.out.println("--------" + session.getAttribute(WxChartConst.ROBOT_RESULT_USERNAMELIST) + "---------");
            return new ResultVO("200", null);
        }
        return new ResultVO("201", null);
    }


}


