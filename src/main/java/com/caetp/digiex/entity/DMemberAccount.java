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
public class DMemberAccount implements IEntity {
    private Integer memberId;

    private Long accountBalance;

    private Long promotionReward;

    private Long differenceReward;

    private Long invitationReward;

    private Long circularReward;

    private Double eenxBalance;

    private Long cnyBalance;

    private Long usdBalance;

    private Long eurBalance;

    private Long hkdBalance;

    private Long freeRechargeAmount;

    private Long freeWithdrawAmount;

    private Long signPointsBalance;

    private Integer createdBy;

    private LocalDateTime createdTime;

    private Integer updatedBy;

    private LocalDateTime updatedTime;
}