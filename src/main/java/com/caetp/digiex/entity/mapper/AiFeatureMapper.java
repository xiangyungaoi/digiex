package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.cms.AiFeatureDTO;
import com.caetp.digiex.entity.AiFeature;

import java.util.List;

public interface AiFeatureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AiFeature record);

    int insertSelective(AiFeature record);

    AiFeature selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AiFeature record);

    int updateByPrimaryKey(AiFeature record);

    List<AiFeatureDTO> list();
}