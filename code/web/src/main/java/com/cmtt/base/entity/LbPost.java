package com.cmtt.base.entity;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LbPost对象", description="文章表")
public class LbPost extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "期刊ID")
    private Integer periodicalId;

    @ApiModelProperty(value = "期刊名称")
    private String periodicalTitle;

    @ApiModelProperty(value = "标题")
    @TableField(condition = SqlCondition.LIKE)
    private String title;

    @ApiModelProperty(value = "免费内容")
    private String content;

    @ApiModelProperty(value = "收费内容")
    private String feeContent;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享内容")
    private String shareContent;

    @ApiModelProperty(value = "诗歌类引用标题")
    private String quoteTitle;
    @ApiModelProperty(value = "诗歌类引用简介")
    private String quoteDesc;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "栏目id")
    private Integer postSubjectId;

    @ApiModelProperty(value = "栏目")
    private String postSubject;

    @ApiModelProperty(value = "分类ID")
    private Integer postCatalogId;

    @ApiModelProperty(value = "分类颜色")
    private String tcolor;

    @ApiModelProperty(value = "分类")
    private String postCatalog;

    @ApiModelProperty(value = "显示样式[1上下图文，0左右图文，2广告类型，3无图]")
    private Integer showType;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

    @ApiModelProperty(value = "详情图片地址")
    private String preimgUrl;

    @ApiModelProperty(value = "广告链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "主题信息")
    private String themeInfo;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "排序")
    private Integer postOrder;

    @ApiModelProperty(value = "是否免费 1免费 2收费")
    private Integer isFree;


    @ApiModelProperty(value = "发布时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedAt;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseCount;

    @ApiModelProperty(value = "收藏数量")
    private Integer recordCount;

    @ApiModelProperty(value = "阅读数量")
    private Integer readCount;

    @ApiModelProperty(value = "是否推荐")
    private Integer recommend;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public List<LbAuthorVo> getLbAuthorList() {

        if(this.author.length()>0) {
            try{
                List<LbAuthorVo> list = JSON.parseObject(this.author, new ArrayList<LbAuthorVo>().getClass());
                return list;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }

    public void setLbAuthorList(List<LbAuthorVo> lbAuthorList) {
        this.lbAuthorList = lbAuthorList;
    }

    @TableField(exist = false)
    private List<LbAuthorVo> lbAuthorList;


    public List<Integer> getLbAuthorIdsList() {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        if(this.author.length()>0) {
            try{
                List<JSONObject> list = JSON.parseObject(this.author, new ArrayList<LbAuthorVo>().getClass());
                list.forEach((s) -> integerArrayList.add(Integer.valueOf(s.get("id").toString())));
                return integerArrayList;
            }catch (Exception e){
                e.printStackTrace();
            }


        }
        return lbAuthorIdsList;
    }

    public void setLbAuthorIdsList(List<Integer> lbAuthorIdsList) {
        this.lbAuthorIdsList = lbAuthorIdsList;
    }

    @TableField(exist = false)
    private List<Integer> lbAuthorIdsList;

}
