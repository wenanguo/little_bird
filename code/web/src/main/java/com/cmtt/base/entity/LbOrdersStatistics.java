package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.cmtt.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

/**
 * <p>
 * 订单统计表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="LbOrdersStatistics对象", description="订单统计表")
public class LbOrdersStatistics extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "渠道")
    private String channel;

    @ApiModelProperty(value = "类型，1包年2单点")
    private Integer ttype;

    @ApiModelProperty(value = "设备类型1安卓2苹果")
    private Integer devType;

    @ApiModelProperty(value = "标题")
    private String tcount;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
