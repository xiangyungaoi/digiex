package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositUserInfo {
    private Integer id;

    private String reqid;

    private Integer memberId;

    private Integer login;

    private BigDecimal amount;

    private LocalDateTime oprationTime;



}