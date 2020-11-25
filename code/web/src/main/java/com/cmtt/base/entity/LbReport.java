package com.cmtt.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 举报表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LbReport对象", description="举报表")
public class LbReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "举报地址")
    private String reportLink;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
