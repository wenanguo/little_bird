package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/*********************************
* @Description: 数据字典 (入参类)
* @author andrew
* @version v2.0
* @date 2019-12-28
* @Company: block
* @Copyright: Copyright (c) 2019-2029
*********************************/
@Data
public class PageInputParam {

    /**
	* 字典编号
	*/
	@ApiModelProperty(value ="页数",example = "1")
	@NotNull(message = "当前页不能为空")
	private Integer pageNo;

    /**
	* 类型名称
	*/
	@ApiModelProperty(value ="页大小",example = "10")
	@NotNull(message = "页大小")
	private Integer pageSize;



}

