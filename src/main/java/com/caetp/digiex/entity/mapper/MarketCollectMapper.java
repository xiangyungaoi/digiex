package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.entity.MarketCollect;
import org.apache.ibatis.annotations.Param;

public interface MarketCollectMapper {
    int deleteByPrimaryKey(@Param("marketId") Long marketId, @Param("memberId") Integer memberId);

    int insert(MarketCollect record);

    int insertSelective(MarketCollect record);

    MarketCollect selectByPrimaryKey(@Param("marketId") Long marketId, @Param("memberId") Integer memberId);
}