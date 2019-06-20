package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppVersions implements IEntity{
    private String versionNo;

    private String versionOs;

    private String updateType;

    private String title;

    private String versionDes;

    private String url;

    private String fileHash;

    private LocalDateTime createdTime;

}