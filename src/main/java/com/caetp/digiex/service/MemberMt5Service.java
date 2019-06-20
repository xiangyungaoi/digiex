package com.caetp.digiex.service;

import com.caetp.digiex.dto.api.MemberMt5DTO;
import com.caetp.digiex.entity.Member;
import com.caetp.digiex.entity.mapper.MemberMt5Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaoyx on 2019/5/24.
 */
@Service
public class MemberMt5Service extends BaseService{

    @Autowired
    private MemberMt5Mapper memberMt5Mapper;
    @Autowired
    private Mt5HandleService mt5HandleService;

    public MemberMt5DTO getUserLeverage(String token) {
        Member member = validateUserToken(token);
        return memberMt5Mapper.getUserLeverage(member.getMemberId().toString());
    }

    public MemberMt5DTO getUserMt5Info(String token) {
        Member member = validateUserToken(token);
        MemberMt5DTO memberMt5DTO = memberMt5Mapper.getUserMt5Info(member.getMemberId().toString());
        return memberMt5DTO;
    }

    public void registerMt5(String token) {
        Member member = validateUserToken(token);
        mt5HandleService.autoRegister(member.getMemberId());
    }
}
