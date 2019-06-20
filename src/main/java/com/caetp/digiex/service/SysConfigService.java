package com.caetp.digiex.service;

import com.caetp.digiex.dto.api.AiOrderConfigDTO;
import com.caetp.digiex.entity.mapper.SysDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangzy on 2019/3/21.
 */
@Service
public class SysConfigService extends BaseService {

    @Autowired
    private SysDataMapper sysDataMapper;


    public AiOrderConfigDTO aiOrder() {
        return AiOrderConfigDTO.builder()
                .leverageTimes(sysDataMapper.selectByPrimaryKey("leverage_times").getValue())
                .pettyCashRate(sysDataMapper.selectByPrimaryKey("petty_cash_rate").getValue())
                .build();
    }
}
