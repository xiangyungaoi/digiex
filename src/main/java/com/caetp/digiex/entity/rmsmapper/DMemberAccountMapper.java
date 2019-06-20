package com.caetp.digiex.entity.rmsmapper;

import com.caetp.digiex.entity.DMemberAccount;
import com.caetp.digiex.entity.DMemberAccountIncrement;

public interface DMemberAccountMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(DMemberAccount record);

    int insertSelective(DMemberAccount record);

    DMemberAccount selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(DMemberAccount record);

    int updateByPrimaryKey(DMemberAccount record);

    int updateAccountByPrimaryKeySelective(DMemberAccountIncrement record);

    DMemberAccount getUserUsdAmount(String memberid);

}