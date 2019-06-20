package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.api.MemberMt5DTO;
import com.caetp.digiex.entity.MemberMt5;
import com.caetp.digiex.entity.Mt5AccountNumber;

import java.util.List;

public interface MemberMt5Mapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(MemberMt5 record);

    int insertSelective(MemberMt5 record);

    MemberMt5 selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(MemberMt5 record);

    int updateByPrimaryKey(MemberMt5 record);

    MemberMt5DTO getUserLeverage(String memberid);

    Integer getMemberIdByLogin(String login);

    int updateEquityByMemberId(MemberMt5 memberMt5);

    MemberMt5DTO getUserMt5Info(String memberid);

    MemberMt5DTO getUserLeverageByLogin(String login);

    int countMemberNumber();

    Long[] countAccount();

    /* Mt5AccountNumber countAccount();*/
}