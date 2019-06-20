package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DMemberAccountDetails implements IEntity {
    private Long id;

    private Integer memberId;

    private String linkId;

    private String accountType;

    private BigDecimal amount;

    private String detailsType;

    private LocalDateTime createdTime;

}