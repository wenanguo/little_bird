package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/*********************************
* @Description: 获取单期数据 (入参类)
* @author andrew
* @version v2.0
*********************************/
@Data
public class WxQueryTradeInputParam {


	@ApiModelProperty(value ="订单编号",example = "1")
	@NotNull(message = "订单编号不能为空")
	private String transaction_id;


}

