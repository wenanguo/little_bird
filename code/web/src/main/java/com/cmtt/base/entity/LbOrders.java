package com.cmtt.base.entity;

import java.math.BigDecimal;
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
import java.io.Serializable;

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
public class LbOrders extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品编号")
    private Integer goodsId;

    @ApiModelProperty(value = "设备类型")
    private Integer devType;

    @ApiModelProperty(value = "类型")
    private Integer ttype;

    @ApiModelProperty(value = "手机号")
    @TableField(condition = SqlCondition.LIKE)
    private String phone;

    @ApiModelProperty(value = "配送方式")
    private String deliveryType;

    @ApiModelProperty(value = "支付宝交易号")
    private String tradeNo;

    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

    @ApiModelProperty(value = "交易状态")
    private String tradeStatus;

    @ApiModelProperty(value = "请求参数")
    private String serverReq;

    @ApiModelProperty(value = "返回参数")
    private String serverResp;

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

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
