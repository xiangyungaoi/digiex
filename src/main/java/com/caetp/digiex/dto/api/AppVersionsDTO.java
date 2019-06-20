package com.caetp.digiex.dto.api;

import com.caetp.digiex.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("App版本")
public class AppVersionsDTO implements BaseDTO {

    @ApiModelProperty("最新版本号")
    private String versionNo;

    @ApiModelProperty("更新类型：1无，2建议更新，3强制更新")
    private String updateType;

    @ApiModelProperty("版本标题")
    private String title;

    @ApiModelProperty("版本描述")
    private String versionDes;

    @ApiModelProperty("apk下载地址")
    private String url;

    @ApiModelProperty("apk文件hash值")
    private String fileHash;

}