package com.cmtt.base.controller.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cmtt.base.utils.AnguoStringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;


/*********************************
* @author andrew
* @version v2.0
*********************************/
@Data
public class AlipayTradeAppPayInputParam {


	@ApiModelProperty(value ="订单金额(测试期间默认为1分)",example = "0.01")
	@NotNull(message = "订单金额不能为空")
	private String total_amount;


	@ApiModelProperty(value ="商品标题/交易标题/订单标题/订单关键字等",example = "Iphone6 16G")
	@NotNull(message = "商品标题不能为空")
	private String subject;

    @ApiModelProperty(value ="对交易或商品的描述",example = "Iphone6 16G")
    @NotNull(message = "商品描述不能为空")
    private String body;



}

