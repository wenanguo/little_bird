package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.cmtt.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

/**
 * <p>
 * 兑换赠送记录表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="LbExchangeGive对象", description="兑换赠送记录表")
public class LbExchangeGive extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "支付渠道交易号")
    private String tradeNo;

    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

    @ApiModelProperty(value = "文章编号")
    private Integer postId;

    @ApiModelProperty(value = "文章标题")
    private String postTitle;

    @ApiModelProperty(value = "用户编号")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户手机号")
    @TableField(condition = SqlCondition.LIKE)
    private String phone;

    @ApiModelProperty(value = "生成人姓名")
    private String genUserName;

    @ApiModelProperty(value = "生成人编号")
    private Integer genUserId;

    @ApiModelProperty(value = "绑定人姓名")
    private String bindUserName;

    @ApiModelProperty(value = "绑定人编号")
    private Integer bindUserId;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "生成时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "绑定时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}
