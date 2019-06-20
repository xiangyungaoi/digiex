package com.caetp.digiex.task;

import com.alibaba.fastjson.JSON;
import com.caetp.digiex.entity.MemberMt5;
import com.caetp.digiex.entity.Mt5AccountNumber;
import com.caetp.digiex.entity.mapper.MemberMt5Mapper;
import com.caetp.digiex.service.Mt5ResultService;
import org.java_websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/** 定时查看所有用户的预付款维持率
 * Created by gaoyx on 2019/6/13.
 */
@Component
public class PreviousPayMentRateTask {
    private static Logger log = LoggerFactory.getLogger(PreviousPayMentRateTask.class);
    @Autowired
    private WebSocketClient webSocketCheckPayMentRate;
    @Autowired
    private MemberMt5Mapper memberMt5Mapper;

    @Scheduled(fixedDelay = 3600000)  //间隔20秒
    public void first() throws InterruptedException {
        log.info("定时查看用户的预付维持率");
        int count = memberMt5Mapper.countMemberNumber();
        Long[] longs = memberMt5Mapper.countAccount();
        int length = longs.length;
        String[] loginitem = new String[length];
        Map<String,Object> reqParameterMap = new HashMap<>();
        for (int i = 0; i< length; i++) {
            Map loginMap = new HashMap<String, Long>();
            loginMap.put("login", longs[i]);
            loginitem[i] = JSON.toJSONString(loginMap);
            log.info(loginitem[i]);
        }

        reqParameterMap.put("reqtype", "marginleveluserinfo");
        reqParameterMap.put("reqid", LocalDateTime.now());
        reqParameterMap.put("count", count);
        reqParameterMap.put("loginItem", loginitem);
        String reqParameter = JSON.toJSONString(reqParameterMap);
        log.info(reqParameter);
        webSocketCheckPayMentRate.send(reqParameter);
    }
}
