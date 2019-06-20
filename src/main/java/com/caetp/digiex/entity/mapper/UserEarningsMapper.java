package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.api.EarningsDTO;
import com.caetp.digiex.dto.api.EarningsUserDTO;
import com.caetp.digiex.entity.UserEarnings;

import java.time.LocalDateTime;
import java.util.List;

public interface UserEarningsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserEarnings record);

    int insertSelective(UserEarnings record);

    UserEarnings selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserEarnings record);

    int updateByPrimaryKey(UserEarnings record);

    List<EarningsDTO> userEarningsList(Integer memberId);

    Double selectTotalEarnings(Integer memberId);

    List<EarningsUserDTO> userEarnings(LocalDateTime dateTime);
}