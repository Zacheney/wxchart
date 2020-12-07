package com.xicheng.wxchart.service;

import com.alibaba.fastjson.JSON;
import com.xicheng.wxchart.VO.RobotRes;
import com.xicheng.wxchart.util.HttpClientRes;
import com.xicheng.wxchart.util.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Classname RobotService
 * @Description 图灵机器人
 * @Date 2020/12/4 17:20
 * @Author zc
 */
@Service
public class RobotService {
    private static final String ROBOT_URL = "http://openapi.tuling123.com/openapi/api/v2";

    public RobotRes doSendMsg(String msg) throws Exception {
        String requestJson = "{\n" +
                "\t\"reqType\":0,\n" +
                "    \"perception\": {\n" +
                "        \"inputText\": {\n" +
                "            \"text\": \"" + msg + "\"\n" +
                "        }\n" +
                "       \n" +
                "    },\n" +
                "    \"userInfo\": {\n" +
                "        \"apiKey\": \"d9a9525ca9cf4534af49a460dfc63d74\",\n" +
                "        \"userId\": \"23\"\n" +
                "    }\n" +
                "}";

        HashMap<String, String> robotHeader = new HashMap<>();
        robotHeader.put("Content-Type", "application/json");
        HttpClientRes httpClientRobotRes = HttpClientUtil.doPost(ROBOT_URL, robotHeader, requestJson);
        RobotRes robotRes = JSON.parseObject(httpClientRobotRes.getContent(), RobotRes.class);
        return robotRes;
    }


}
