package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/*********************************
* @author andrew
* @version v2.0
*********************************/
@Data
public class ApplePayValidInputParam {


	@ApiModelProperty(value ="小票凭证（base64加密）",example = "abcd")
	@NotNull(message = "小票凭证不能为空")
	private String receipt_data;


    @ApiModelProperty(value ="商户订单号",example = "123456789")
    @NotNull(message = "商户订单号不能为空")
    private String out_trade_no;


	@ApiModelProperty(value ="苹果商品",example = "avesproduct10001")
	@NotNull(message = "苹果商品不能为空")
	private String product_id;

	@ApiModelProperty(value ="交易流水号",example = "a100001")
	@NotNull(message = "交易流水号不能为空")
	private String transaction_id;

	@ApiModelProperty(value ="商品编号",example = "1001")
	@NotNull(message = "商品编号不能为空")
	private String tcode;


}

