package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.entity.UserMt5OrderDetail;

import java.util.List;

public interface UserMt5OrderDetailMapper {
    int deleteByPrimaryKey(Integer order);

    int insert(UserMt5OrderDetail record);

    int insertSelective(UserMt5OrderDetail record);

    UserMt5OrderDetail selectByPrimaryKey(Integer order);

    int updateByPrimaryKeySelective(UserMt5OrderDetail record);

    int updateByPrimaryKey(UserMt5OrderDetail record);

    List<UserMt5OrderDetail> getUserManualHistoryOrder(Integer login);

    List<UserMt5OrderDetail> getCencleOrders(String login);

}