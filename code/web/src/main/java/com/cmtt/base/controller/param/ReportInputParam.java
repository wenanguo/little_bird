package com.cmtt.base.controller.param;

import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupEdit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Andrew.Wen
 * 2020/11/25.
 */
@Data
public class ReportInputParam {

    @ApiModelProperty(value = "手机号", example = "15285027249")
    @NotNull(
            message = "手机号不能为空"
    )
    private String phone;

    @ApiModelProperty(value = "邮箱", example = "wenanguo110@163.com")
    @NotNull(

            message = "邮箱不能为空"
    )
    private String email;

    @ApiModelProperty(value = "举报地址", example = "https://www.baidu.com")
    @NotNull(
            message = "举报地址不能为空"
    )
    private String reportLink;


    @ApiModelProperty(value = "原因", example = "垃圾推广")
    @NotNull(
            message = "原因不能为空"
    )
    private String reason;


}
