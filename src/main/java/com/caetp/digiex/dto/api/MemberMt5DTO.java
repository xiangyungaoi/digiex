package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gaoyx on 2019/5/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户mt5账户信息")
public class MemberMt5DTO implements BaseDTO {
    @ApiModelProperty("mt5的login")
    private Integer login;
    @ApiModelProperty("mt5用户名")
    private String username;
    @ApiModelProperty("mt5的密码")
    private String password;
    @ApiModelProperty("mt5的投资者密码")
    private String passwordInvestor;
    @ApiModelProperty("mt5的手机密码")
    private String passwordPhone;
    @ApiModelProperty("杠杆")
    private Integer leverage;
}
