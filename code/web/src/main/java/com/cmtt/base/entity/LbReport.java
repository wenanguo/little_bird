package com.cmtt.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.entity.validated.GroupLogin;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

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
public class LbReport extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "手机号",example = "15285027249")
    @NotNull(
            groups = {GroupAdd.class, GroupEdit.class},
            message = "手机号不能为空"
    )
    private String phone;

    @ApiModelProperty(value = "邮箱",example = "wenanguo110@163.com")
    @NotNull(
            groups = {GroupAdd.class, GroupEdit.class},
            message = "邮箱不能为空"
    )
    private String email;

    @ApiModelProperty(value = "举报地址",example = "https://www.baidu.com")
    @NotNull(
            groups = {GroupAdd.class, GroupEdit.class},
            message = "举报地址不能为空"
    )
    private String reportLink;

    @ApiModelProperty(value = "原因",example = "垃圾推广")
    @NotNull(
            groups = {GroupAdd.class, GroupEdit.class},
            message = "原因不能为空"
    )
    private String reason;


    private Integer status;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
