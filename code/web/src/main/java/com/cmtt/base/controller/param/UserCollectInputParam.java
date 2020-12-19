package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Andrew.Wen
 * 2020/11/25.
 */
@Data
public class UserCollectInputParam {

    @ApiModelProperty(value = "文章ID ",example = "2")
    @NotNull(message = "文章编号不能为空")
    private Integer postId;

}
