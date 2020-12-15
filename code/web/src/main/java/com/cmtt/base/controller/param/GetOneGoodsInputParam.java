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
public class GetOneGoodsInputParam {


	@ApiModelProperty(value ="商品编号",example = "1001")
	@NotNull(message = "商品编号不能为空")
	private Integer tcode;


}

