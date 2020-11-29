package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Andrew.Wen
 * 2020/11/25.
 */
@Data
public class GetCatalogPostInputParam {

    @ApiModelProperty(value ="id",example = "1")
    @NotNull(message = "分类ID不能为空")
    private Integer catalogId;



}
