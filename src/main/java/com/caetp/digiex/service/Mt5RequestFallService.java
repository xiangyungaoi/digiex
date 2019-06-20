package com.caetp.digiex.service;

import com.alibaba.fastjson.JSON;
import com.caetp.digiex.utli.common.GetMemberId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by gaoyx on 2019/6/4.
 */
@Service
public class Mt5RequestFallService {
    @Autowired
    Mt5HandleService mt5HandleService;

    public void registerFall(String reqParameter, String result) {
        // 注册失败，重新发送注册请求
        mt5HandleService.autoRegister( Integer.parseInt(GetMemberId.getMemberId(reqParameter)));
    }
}
