package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/*********************************
* @author andrew
* @version v2.0
*********************************/
@Data
public class ApplePayVisitorBindInputParam {




    @ApiModelProperty(value ="商户订单号",example = "123456789")
    @NotNull(message = "商户订单号不能为空")
    private String out_trade_no;




}

