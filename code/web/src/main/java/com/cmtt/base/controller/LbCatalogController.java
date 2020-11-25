package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.LbCatalog;
import com.cmtt.base.entity.LbSubject;
import com.cmtt.base.entity.R;
import com.cmtt.base.service.ILbCatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


    @PostMapping("list")
    @ResponseBody
    @ApiOperation("分类文章列表")
    public R list(@RequestBody @Valid PageInputParam params){

        // 构建分页类
        IPage<LbCatalog> lbCatalogPage = new Page<>(params.getPageNo(), params.getPageSize());

        // 构造查询及排序方式
        QueryWrapper<LbSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, params.getIsAsc(), params.getIsSortField());

        // 执行查询
        lbCatalogPage = lbCatalogService.getLbCatalogPostList(lbCatalogPage);
        R r=R.ok();
        r.setPageResult(lbCatalogPage);
        return r;
    }

}
