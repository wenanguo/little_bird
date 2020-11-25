package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Andrew.Wen
 * 2020/11/25.
 */
@Data
public class UserCollectInputParam {

    @ApiModelProperty(value = "文章ID ",example = "2")
    private Integer postId;

}
