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
public class DMember implements IEntity {
    private Integer memberId;

    private String areaCode;

    private String mobile;

    private String nickName;

    private Boolean isReal;

    private String password;

    private String dealPassword;

    private String token;

    private String avatarImage;

    private Boolean accountStatus;

    private Integer memberLevel;

    private Integer invitationCode;

    private Integer createdBy;

    private LocalDateTime createdTime;

    private Integer updatedBy;

    private LocalDateTime updatedTime;

    private Integer invitedBy;

    private LocalDateTime invitedTime;

    private Integer oldInvitedBy;

    private String updateInvitedDesc;

    private LocalDateTime updateInvitedTime;

    private Integer legalMemberId;
}