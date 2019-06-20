package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.api.MarketDTO;
import com.caetp.digiex.entity.Market;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Market record);

    int insertSelective(Market record);

    Market selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Market record);

    int updateByPrimaryKey(Market record);

    List<MarketDTO> list(@Param("memberId") Integer memberId, @Param("coinType") String coinType);

    List<MarketDTO> collectList(@Param("memberId") Integer memberId);

    String getCoinsString(@Param("coinType") String coinType);

    List<MarketDTO> searchList(String keyword);

    List<MarketDTO> getCoinName();

    /*List<MarketDTO> searchSymbolList(@Param("keyWord") String keyWord);*/
}