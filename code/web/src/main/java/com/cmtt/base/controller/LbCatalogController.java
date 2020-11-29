package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.GetCatalogPostInputParam;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.service.ILbCatalogService;
import com.cmtt.base.service.ILbPostService;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/lb_catalog")
@Api(tags = "主页相关")
public class LbCatalogController {


    @Autowired
    private ILbCatalogService lbCatalogService;

    @Autowired
    private ILbPostService lbPostService;


    @PostMapping("list")
    @ResponseBody
    @ApiOperation("分类文章列表")
    public R list(){


        // 执行查询
        //List<LbCatalog> lbCatalogPostList= lbCatalogService.getLbCatalogPostList();
        List<LbCatalog> lbCatalogPostList= lbCatalogService.list(Wrappers.<LbCatalog>lambdaQuery().eq(LbCatalog::getStatus, RC.B_NORMAL.code()));

        return R.ok().setResult(lbCatalogPostList);
    }

    @PostMapping("get_catalog_post")
    @ResponseBody
    @ApiOperation("获取当前分类文章列表")
    public R getCatalogPost(@RequestBody @Valid GetCatalogPostInputParam params){

        // 执行查询
        List<LbPost> lbCatalogPostList= lbPostService.list(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getPostCatalogId,params.getCatalogId()).last("limit 0,5"));;


        return R.ok().setResult(lbCatalogPostList);
    }

}
