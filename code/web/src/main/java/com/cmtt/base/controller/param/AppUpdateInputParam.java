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
public class AppUpdateInputParam {


	@ApiModelProperty(value ="当前版本",example = "10")
	@NotNull(message = "当前版本")
	private Integer curVersion;


}

