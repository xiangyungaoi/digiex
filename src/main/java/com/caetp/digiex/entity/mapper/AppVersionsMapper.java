package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.api.AppVersionsDTO;
import com.caetp.digiex.entity.AppVersions;
import org.apache.ibatis.annotations.Param;

public interface AppVersionsMapper {
    int deleteByPrimaryKey(@Param("versionNo") String versionNo, @Param("versionOs") String versionOs);

    int insert(AppVersions record);

    int insertSelective(AppVersions record);

    AppVersions selectByPrimaryKey(@Param("versionNo") String versionNo, @Param("versionOs") String versionOs);

    int updateByPrimaryKeySelective(AppVersions record);

    int updateByPrimaryKey(AppVersions record);

    AppVersionsDTO getLastVersion(@Param("apiVersion")String apiVersion, @Param("deviceType")String deviceType);
}