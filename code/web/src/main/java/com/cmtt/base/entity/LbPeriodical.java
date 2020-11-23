package com.cmtt.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 期刊表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LbPeriodical对象", description="期刊表")
@Accessors(chain = true)
public class LbPeriodical implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "期刊编号")
    private String tcode;

    @ApiModelProperty(value = "期刊封面URL")
    private String imgUrl;

    @ApiModelProperty(value = "推荐期刊【1推荐，2正常】")
    private Integer recommend;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    private List<LbPost> lbPostList;


}