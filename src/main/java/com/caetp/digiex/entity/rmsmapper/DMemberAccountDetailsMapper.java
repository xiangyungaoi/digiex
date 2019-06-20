package com.caetp.digiex.entity.rmsmapper;

import com.caetp.digiex.entity.DMemberAccountDetails;

public interface DMemberAccountDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DMemberAccountDetails record);

    int insertSelective(DMemberAccountDetails record);

    DMemberAccountDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DMemberAccountDetails record);

    int updateByPrimaryKey(DMemberAccountDetails record);

}