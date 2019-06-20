package com.caetp.digiex.dto.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shijy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("AI-用户名")
public class AIRobotCmsNameDTO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("AI名称")
    private String name;
}
