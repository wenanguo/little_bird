package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.entity.validated.GroupLogin;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品订单表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="LbOrders对象", description="商品订单表")
public class SysUserOrders extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(
            type = IdType.AUTO
    )
    @NotNull(
            groups = {GroupEdit.class},
            message = "ID不能为空"
    )
    private Integer id;
    @ApiModelProperty("用户名")
    @NotNull(
            groups = {GroupAdd.class, GroupEdit.class, GroupLogin.class},
            message = "用户名不能为空"
    )
    @TableField(condition = SqlCondition.LIKE)
    private String username;
    @ApiModelProperty("密码")
    @NotNull(
            groups = {GroupAdd.class, GroupEdit.class, GroupLogin.class},
            message = "密码不能为空"
    )
    private String password;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("组织ID")
    private Integer orgId;
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("备注")
    private String memo;
    @ApiModelProperty("性别")
    private Integer sex;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("头像")
    private String icon;
    @ApiModelProperty("类别，1普通，2包月，3管理员")
    private String ttype;
    @ApiModelProperty("微信id")
    private String wxId;
    @ApiModelProperty("qqid")
    private String qqId;
    @ApiModelProperty("微博id")
    private String wbId;
    @ApiModelProperty("最后登录信息")
    private String last_login;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("修改时间")
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime updateTime;
    @ApiModelProperty("创建时间")
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime createTime;



    @ApiModelProperty(value = "手机号")
    private String orderPhone;

    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

    @ApiModelProperty(value = "交易状态")
    private String tradeStatus;

    @ApiModelProperty(value = "订单类型")
    private Integer orderType;


    @ApiModelProperty(value = "商品总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal receiptAmount;

    @ApiModelProperty(value = "开票金额")
    private BigDecimal invoiceAmount;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal buyerPayAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discount;

    @ApiModelProperty(value = "总退款金额")
    private BigDecimal refundFee;

    @ApiModelProperty(value = "交易创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "交易付款时间")
    private LocalDateTime gmtPayment;

    @ApiModelProperty(value = "交易退款时间")
    private LocalDateTime gmtRefund;

    @ApiModelProperty(value = "交易结束时间")
    private LocalDateTime gmtClose;

}
