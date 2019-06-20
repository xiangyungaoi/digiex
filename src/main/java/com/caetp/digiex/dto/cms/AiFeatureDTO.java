package com.caetp.digiex.dto.cms;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangzy on 2019/4/28.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("AI标签")
public class AiFeatureDTO implements BaseDTO {

    private Long id;

    @ApiModelProperty("标签名")
    private String feature;
}
