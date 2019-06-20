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
public class DMemberAccountIncrement implements IEntity {
    private Integer memberId;

    private Long accountBalanceIncrement;

    private Long promotionRewardIncrement;

    private Long differenceRewardIncrement;

    private Long invitationRewardIncrement;

    private Long circularRewardIncrement;

    private Double eenxBalanceIncrement;

    private Long cnyBalanceIncrement;

    private Long usdBalanceIncrement;

    private Long eurBalanceIncrement;

    private Long hkdBalanceIncrement;

    private Long freeRechargeAmountIncrement;

    private Long freeWithdrawAmountIncrement;

    private Long signPointsBalanceIncrement;

    private Integer updatedBy;

    private LocalDateTime updatedTime;
}