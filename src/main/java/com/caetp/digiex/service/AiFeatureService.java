package com.caetp.digiex.service;

import com.caetp.digiex.dto.cms.AiFeatureDTO;
import com.caetp.digiex.entity.AiFeature;
import com.caetp.digiex.entity.mapper.AiFeatureMapper;
import com.caetp.digiex.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by wangzy on 2019/4/28.
 */
@Service
public class AiFeatureService extends BaseService {

    @Autowired
    private AiFeatureMapper aiFeatureMapper;

    private static final int FEATURE_MAX_LENGTH = 6;

    /**
     * AI标签列表
     * @return
     */
    public List<AiFeatureDTO> list() {
        return aiFeatureMapper.list();
    }

    /**
     * 新增标签
     * @param feature
     */
    public void add(String feature) {
        if (feature.length() > FEATURE_MAX_LENGTH) {
            // 标签长度不超过最大字符长度
            throw CommonException.INVALID_PARAMETER;
        }

        aiFeatureMapper.insertSelective(AiFeature.builder()
                .feature(feature).createdTime(LocalDateTime.now())
                .build());
    }

    /**
     * 删除标签
     * @param id
     */
    public void delete(Long id) {
        aiFeatureMapper.deleteByPrimaryKey(id);
    }

}
