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
public class GetAdInputParam {


	@ApiModelProperty(value ="广告类型【1 启动广告，2 首页广告】",example = "1")
	@NotNull(message = "类型不能为空")
	private Integer ad_type;


}

