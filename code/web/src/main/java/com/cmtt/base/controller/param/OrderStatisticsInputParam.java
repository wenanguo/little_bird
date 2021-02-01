package com.cmtt.base.controller.param;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;



/*********************************
* @Description: 获取订单统计数据 (入参类)
* @author andrew
* @version v2.0
*********************************/
@Data
public class OrderStatisticsInputParam {



	@ApiModelProperty(value = "开始时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;




}

