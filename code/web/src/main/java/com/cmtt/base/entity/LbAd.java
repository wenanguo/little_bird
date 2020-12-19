package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 广告表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LbAd对象", description="广告表")
public class LbAd extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    @ApiModelProperty(value = "介绍")
    private String introduction;

    @ApiModelProperty(value = "图片")
    private String imgUrl;

    @ApiModelProperty(value = "广告分类[1外部链接 2作者广告 3栏目广告]")
    private Integer adType;

    @ApiModelProperty(value = "广告位置[1首页广告 2启动广告]")
    private Integer adLocation;

    @ApiModelProperty(value = "所属期刊")
    private Integer lbPeriodicalId;

    @ApiModelProperty(value = "所属期刊名称")
    private String lbPeriodicalTitle;

    @ApiModelProperty(value = "所属期刊文章位置")
    private Integer lbPeriodicalIndex;

    @ApiModelProperty(value = "所属栏目id")
    private Integer lbSubjectId;

    @ApiModelProperty(value = "所属栏目名称")
    private String lbSubjectTitle;

    @ApiModelProperty(value = "所属作者id")
    private Integer lbAuthorId;

    @ApiModelProperty(value = "所属作者名称")
    private String lbAuthorName;

    @ApiModelProperty(value = "连接地址")
    private String linkUrl;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
