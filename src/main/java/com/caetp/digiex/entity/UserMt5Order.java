package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMt5Order {
    private Long order;

    private Integer memberId;

    private Integer login;

    private String symbol;

    private Double volumeInitial;

    private Double volumeCurrent;

    private Double profit;

    private Double rateMargin;

    private Double rateProfit;

    private Double storage;

    private Integer contractSize;

    private Integer digits;

    private Integer digitsCurrency;

    private Double priceCurrent;

    private Double priceOrder;

    private Double priceOpen;

    private Double pricesl;

    private Double pricetp;

    private Double priceTrigger;

    private String comment;

    private Integer reason;

    private Integer state;

    private Integer type;

    private Integer action;

    private Integer typeFill;

    private Date timeSetup;

    private Date timeDone;

    private Date timeExpiration;

    private Date timeUpdate;

    private Date typeTime;

    private Long spread;

    private Integer expiration;

    private Long leverage;

    private Long positionId;


}