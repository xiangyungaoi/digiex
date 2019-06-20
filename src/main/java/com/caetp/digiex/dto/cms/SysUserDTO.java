package com.caetp.digiex.dto.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shijy
 * @date 2019/2/25 18:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("系统用户")
public class SysUserDTO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户状态")
    private Integer userStatus;

    @ApiModelProperty("姓名")
    private String name;
}
