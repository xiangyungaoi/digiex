package com.caetp.digiex.service;

import com.caetp.digiex.entity.MemberMt5;
import com.caetp.digiex.entity.mapper.MemberMt5Mapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 注册mt5账户
 * Created by gaoyx on 2019/5/15.
 */
@Service
public class RegisterService {

    @Autowired
    MemberMt5Mapper memberMt5Mapper;
    /**注册mt5账户
     * @param reqParamerMap
     * @param resultMap
     */
    public void register(Map reqParamerMap, Map resultMap) {
    // 将返回的login和相关的信息 和 member_id(在请求参数中的reqid中)绑定起来，存到member_mt5表中
            String reqid = (String) resultMap.get("reqid");
            String[] split = reqid.split("\\.");
            MemberMt5 memberMt5 = MemberMt5.builder().memberId(Integer.parseInt(split[0])).login((Integer) resultMap.get("login"))
                    .username((String) reqParamerMap.get("username")).leverage((Integer) reqParamerMap.get("leverage"))
                    .groupname((String) reqParamerMap.get("groupname")).password((String) reqParamerMap.get("password"))
                    .passwordInvestor((String) reqParamerMap.get("investor"))
                    .passwordPhone((String) reqParamerMap.get("phonepwd"))
                    .agentAccount((Integer) reqParamerMap.get("agentaccount"))
                    .comment((String) reqParamerMap.get("comment"))
                    .email((String) reqParamerMap.get("email")).country((String) reqParamerMap.get("country"))
                    .state((String) reqParamerMap.get("state")).city((String) reqParamerMap.get("city"))
                    .address((String) reqParamerMap.get("address")).phone((String) reqParamerMap.get("phone"))
                    .zipcode((String) reqParamerMap.get("zipcode")).build();
            memberMt5Mapper.insertSelective(memberMt5);
    }
}
