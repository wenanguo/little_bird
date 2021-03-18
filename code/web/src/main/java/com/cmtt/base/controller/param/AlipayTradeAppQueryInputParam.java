package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/*********************************
* @author andrew
* @version v2.0
*********************************/
@Data
public class AlipayTradeAppQueryInputParam {


//	@ApiModelProperty(value ="订单金额(测试期间默认为1分)",example = "0.01")
//	@NotNull(message = "订单金额不能为空")
//	private String total_amount;


	@ApiModelProperty(value ="支付宝订单号",example = "12345678")
	// @NotNull(message = "支付宝订单号不能为空")
	private String trade_no;

    @ApiModelProperty(value ="商户订单号",example = "123456789")
    @NotNull(message = "商户订单号不能为空")
    private String out_trade_no;


//	@ApiModelProperty(value ="卖方ID",example = "123456789")
//	@NotNull(message = "卖方ID不能为空")
//	private String seller_id;


}

