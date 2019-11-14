package com.xicheng.wxchart.controller;

import com.xicheng.wxchart.service.WxChartService;
import com.xicheng.wxchart.util.ResultVO;
import jdk.nashorn.internal.objects.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class WxChartController {

    @Autowired
    private  WxChartService wxChartService;

    @RequestMapping("wxLogin")
    public String wxChart() throws Exception{
            //生成二维码地址
        String qcCodeEndUrl = wxChartService.getQcCode();
        System.out.println(qcCodeEndUrl);
        return String.format("<img src= 'https://login.weixin.qq.com/qrcode/%s' />",qcCodeEndUrl);
    }


    @RequestMapping("isLogin")
    public ResultVO isLogin(String qcCodeEndUrl) throws Exception {
        String content = wxChartService.isLogin(qcCodeEndUrl);
        System.out.println(content);
        String[] contents = content.split(";");
        String windowCode = contents[0];
        if(windowCode.endsWith("408")){
            return new ResultVO("408",null);
        }else if(windowCode.endsWith("201")){
            String imgData = contents[1].split("\'")[1];
            return new ResultVO("201",imgData);
        }else{
            String loginEndUrl = contents[1].split("\"")[1].substring(56);
            return new ResultVO("200",loginEndUrl);
        }
    }


    @RequestMapping("/wxCheck/{code}")
    public void wx(@PathVariable("code")String code) throws Exception {
        ResultVO  login = null;
        for (;;){
             login = isLogin(code);
            System.out.println(login.getData());
            if(login.getCode().equals("200")){
                break;
            }
        }
        wxChartService.excute((String)login.getData());
    }


}


