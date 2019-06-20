package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * created by hy on 2018/12/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("公告详情")
public class SystemNoticeDetailDTO implements BaseDTO {

    private Long id;
    
    @ApiModelProperty("公告标题")
    private String title;
    
    private LocalDateTime createdTime;
    
    @ApiModelProperty("公告内容")
    private String content;
}
