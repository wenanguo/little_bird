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
public class PageInputParam {


	@ApiModelProperty(value ="页数",example = "1")
	@NotNull(message = "当前页不能为空")
	private Integer pageNo;


	@ApiModelProperty(value ="页大小",example = "10")
	@NotNull(message = "页大小")
	private Integer pageSize;



    @ApiModelProperty(value ="排序字段",example = "id")
    private String sortField;
    @TableField(
            exist = false
    )

    @ApiModelProperty(value ="排序方式",example = "desc")
    private String sortOrder;

    @JsonIgnore
    public boolean getIsAsc() {
        boolean isAsc = false;
        if (!StringUtils.isEmpty(this.getSortOrder()) && this.getSortOrder().equals("ascend")) {
            isAsc = true;
        }

        return isAsc;
    }

    @JsonIgnore
    public String getIsSortField() {
        String field = "id";
        if (!StringUtils.isEmpty(this.getSortField())) {
            field = AnguoStringUtils.humpToUnderline(this.getSortField());
        }

        return field;
    }



}

