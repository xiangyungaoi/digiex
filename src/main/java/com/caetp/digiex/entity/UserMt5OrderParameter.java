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
public class UserMt5OrderParameter {
    private Integer order;

    private String reqid;

    private Integer login;

    private String symbol;

    private Integer tradeType;

    private Integer tradecmd;

    private BigDecimal price;

    private Double spread;

    private Double initialVolume;

    private Integer fillPolicy;

    private Integer expiration;

    private LocalDateTime expirationDat;

    private BigDecimal sl;

    private BigDecimal tp;

    private String orderby;

    private String comment;

    private Integer leverage;


}