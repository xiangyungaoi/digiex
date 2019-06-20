package com.caetp.digiex.entity.rmsmapper;

import com.caetp.digiex.entity.DMember;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DMemberMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(DMember record);

    int insertSelective(DMember record);

    DMember selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(DMember record);

    int updateByPrimaryKey(DMember record);

    DMember selectByMobile(@Param("areaCode") String areaCode, @Param("mobile") String mobile);

}