package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/*********************************
* @author andrew
* @version v2.0
*********************************/
@Data
public class ApplePayCreateInputParam {


	@ApiModelProperty(value ="小票凭证（base64加密）",example = "0.01")
	@NotNull(message = "小票凭证不能为空")
	private String receipt_data;


    @ApiModelProperty(value ="商户订单号",example = "123456789")
    @NotNull(message = "商户订单号不能为空")
    private String tcode;


	@ApiModelProperty(value ="手机号",example = "15285027249")
	@NotNull(message = "手机号不能为空")
	private String phone;


}

