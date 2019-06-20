package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMt5 {
    private Integer memberId;

    private Integer login;

    private String username;

    private String password;

    private String passwordInvestor;

    private String passwordPhone;

    private Integer leverage;

    private Double balance;

    private Double equity;

    private Double margin;

    private Double marginFree;

    private Double marginLevel;

    private Double credit;

    private Integer interestrate;

    private Integer taxes;

    private String groupname;

    private String company;

    private String status;

    private Integer language;

    private String email;

    private String phone;

    private String country;

    private String state;

    private String city;

    private String zipcode;

    private String address;

    private String comment;

    private String color;

    private Integer agentAccount;

    private Integer agentDelete;

    private Integer enable;

    private Integer enableTrade;

    private Integer enableEa;

    private Integer enablewebapi;

    private Integer enableChangePassword;

    private Integer sendReports;

    private Integer enableTradesl;

    private Integer enablenextmodpwd;

    private Integer enableotp;


}