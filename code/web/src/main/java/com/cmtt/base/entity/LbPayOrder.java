package com.cmtt.base.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 支付订单表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="LbPayOrder对象", description="支付订单表")
public class LbPayOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品标题")
    private String subject;

    @ApiModelProperty(value = "商品描述")
    private String body;

    @ApiModelProperty(value = "金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "创建订单相应")
    private String tradeAppPayResponse;

    @ApiModelProperty(value = "支付宝交易号")
    private String tradeNo;

    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "收款支付宝账号对应的支付宝唯一用户号")
    private String sellerId;

    @ApiModelProperty(value = "收款支付宝账号")
    private String sellerEmail;

    @ApiModelProperty(value = "商户原始订单号")
    private String merchantOrderNo;

    @ApiModelProperty(value = "买家支付宝用户号")
    private String buyerId;

    @ApiModelProperty(value = "买家支付宝账号")
    private String buyerLogonId;

    @ApiModelProperty(value = "交易状态")
    private String tradeStatus;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal receiptAmount;

    @ApiModelProperty(value = "开票金额")
    private BigDecimal invoiceAmount;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal buyerPayAmount;

    @ApiModelProperty(value = "集分宝金额")
    private BigDecimal pointAmount;

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

    @ApiModelProperty(value = "支付金额信息")
    private String fundBillList;

    @ApiModelProperty(value = "回传参数")
    private String passbackParams;

    @ApiModelProperty(value = "优惠券信息")
    private String voucherDetailList;

    @ApiModelProperty(value = "异步通知相应")
    private String notifyResponse;


}
