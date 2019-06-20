package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.entity.SysData;

public interface SysDataMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysData record);

    int insertSelective(SysData record);

    SysData selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysData record);

    int updateByPrimaryKey(SysData record);
}