package com.cmtt.base.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;



/*********************************
* @Description: 获取订单统计数据 (入参类)
* @author andrew
* @version v2.0
*********************************/
@Data
public class OrderStatisticsInputParam {



	@ApiModelProperty(value = "开始时间")
	private String startTime;

	@ApiModelProperty(value = "结束时间")
	private String endTime;




}

