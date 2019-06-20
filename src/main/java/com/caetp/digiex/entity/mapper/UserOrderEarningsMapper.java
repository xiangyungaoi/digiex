package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.api.EarningsDTO;
import com.caetp.digiex.dto.api.EarningsOrderDTO;
import com.caetp.digiex.entity.UserOrderEarnings;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserOrderEarningsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserOrderEarnings record);

    int insertSelective(UserOrderEarnings record);

    UserOrderEarnings selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserOrderEarnings record);

    int updateByPrimaryKey(UserOrderEarnings record);

    List<EarningsDTO> userOrderEarningsList(@Param("userOrderId") Long userOrderId, @Param("memberId") Integer memberId);

    Double selectTotalEarnings(@Param("userOrderId")Long userOrderId, @Param("memberId")Integer memberId);

    List<EarningsOrderDTO> orderEarnings(LocalDateTime dateTime);
}