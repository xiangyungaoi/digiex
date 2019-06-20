package com.caetp.digiex.service;

import com.caetp.digiex.dto.api.AppVersionsDTO;
import com.caetp.digiex.entity.mapper.AppVersionsMapper;
import com.caetp.digiex.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangzy on 2018/11/26.
 */
@Service
public class AppVersionsService extends BaseService {

    @Autowired
    private AppVersionsMapper appVersionsMapper;

    /**
     * 获取最新更新版本
     * @param apiVersion
     * @param deviceType
     * @return
     */
    public AppVersionsDTO getLastVersion(String apiVersion, String deviceType) {
        AppVersionsDTO appVersionsDTO = appVersionsMapper.getLastVersion(apiVersion, deviceType);
        if (appVersionsDTO == null) {
            appVersionsDTO = AppVersionsDTO.builder()
                    .versionNo(apiVersion).fileHash("").title("")
                    .updateType("1").url("").versionDes("")
                    .build();
        }
        if (apiVersion.equals(appVersionsDTO.getVersionNo())) {
            appVersionsDTO.setUpdateType("1");
        }
        return appVersionsDTO;
    }
}
