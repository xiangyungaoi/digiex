package com.caetp.digiex.dto.api;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by wangzy on 2018/12/8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("手机区号")
public class AreaCodeDTO implements Serializable {

    private Long id;

    private String area;

    private String code;

}
