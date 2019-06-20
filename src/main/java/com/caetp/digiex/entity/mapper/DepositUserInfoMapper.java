package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.entity.DepositUserInfo;

public interface DepositUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepositUserInfo record);

    int insertSelective(DepositUserInfo record);

    DepositUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepositUserInfo record);

    int updateByPrimaryKey(DepositUserInfo record);
}