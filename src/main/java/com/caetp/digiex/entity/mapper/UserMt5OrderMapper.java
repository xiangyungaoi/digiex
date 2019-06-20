package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.entity.UserMt5Order;

public interface UserMt5OrderMapper {
    int deleteByPrimaryKey(Long order);

    int insert(UserMt5Order record);

    int insertSelective(UserMt5Order record);

    UserMt5Order selectByPrimaryKey(Long order);

    int updateByPrimaryKeySelective(UserMt5Order record);

    int updateByPrimaryKey(UserMt5Order record);
}