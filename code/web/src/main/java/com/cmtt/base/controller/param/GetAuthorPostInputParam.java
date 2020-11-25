package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Andrew.Wen
 * 2020/11/25.
 */
@Data
public class GetAuthorPostInputParam {

    @ApiModelProperty(value ="id",example = "1")
    @NotNull(message = "作者ID不能为空")
    private Integer id;

    @ApiModelProperty(value ="页数",example = "1")
    @NotNull(message = "当前页不能为空")
    private Integer pageNo;


    @ApiModelProperty(value ="页大小",example = "10")
    @NotNull(message = "页大小")
    private Integer pageSize;


}
