package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.entity.UserMt5OrderParameter;

import java.util.List;

public interface UserMt5OrderParameterMapper {
    int deleteByPrimaryKey(Integer order);

    int insert(UserMt5OrderParameter record);

    int insertSelective(UserMt5OrderParameter record);

    UserMt5OrderParameter selectByPrimaryKey(Integer order);

    int updateByPrimaryKeySelective(UserMt5OrderParameter record);

    int updateByPrimaryKey(UserMt5OrderParameter record);

    List<UserMt5OrderParameter> getStarPriceByLogin(Integer login);
}