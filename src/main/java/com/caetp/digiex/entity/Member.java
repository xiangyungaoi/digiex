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
public class Member implements IEntity {
    private Integer memberId;

    private String token;

    private String nickName;

    private String areaCode;

    private String mobile;

    private Boolean isLocked;

    private LocalDateTime lockedTime;

    private Integer errorTimes;

    private Integer createdBy;

    private LocalDateTime createdTime;

    private Integer updatedBy;

    private LocalDateTime updatedTime;

}