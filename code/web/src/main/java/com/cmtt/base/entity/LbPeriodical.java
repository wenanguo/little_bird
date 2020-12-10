package com.cmtt.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class LbPeriodical extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "期刊编号")
    private String tcode;

    @ApiModelProperty(value = "期刊封面URL")
    private String imgUrl;

    @ApiModelProperty(value = "推荐期刊【1推荐，2正常】")
    private Integer recommend;

    @ApiModelProperty(value = "所属年份")
    private String tyear;

    @ApiModelProperty(value = "排序")
    private String torder;

    @ApiModelProperty(value = "pdf下载")
    private String tpdf;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private List<LbPost> lbPostList;



}
