package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketCollect implements IEntity {
    private Long marketId;

    private Integer memberId;
}