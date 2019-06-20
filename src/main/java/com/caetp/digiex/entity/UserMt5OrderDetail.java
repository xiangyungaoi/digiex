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
public class UserMt5OrderDetail {
    private Integer order;

    private Integer positionId;

    private String symbol;

    private Integer orderType;

    private Integer login;

    private Integer digits;

    private Integer type;

    private Double finalVolume;

    private LocalDateTime doneTime;

    private Integer state;

    private BigDecimal orderPrice;

    private BigDecimal sl;

    private BigDecimal tp;

    private LocalDateTime expirationTime;

    private Integer reason;

    private Double marginrate;

    private String comment;

    private LocalDateTime setupTime;

    private Integer fillType;


}