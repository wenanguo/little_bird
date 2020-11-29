package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.GetAdInputParam;
import com.cmtt.base.controller.param.GetAuthorPostInputParam;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.service.ILbAdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 广告表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/lb_ad")
@Api(tags = "主页相关")
public class LbAdController {

    @Autowired
    private ILbAdService iLbAdService;


    /**
     * 获取启动广告
     */
    @PostMapping("get_start_ad")
    @ResponseBody
    @ApiOperation("获取启动广告")
    public R getStartAd(){

        LbAd lbAd = iLbAdService.getOne(Wrappers.<LbAd>lambdaQuery().eq(LbAd::getAdType,1));

        return R.ok().setResult(lbAd);

    }


    /**
     * 获取首页广告
     */
    @PostMapping("get_index_ad")
    @ResponseBody
    @ApiOperation("获取首页广告")
    public R getIndexAd(@RequestBody @Valid PageInputParam params){

        // 构建分页类
        IPage<LbAd> lbAdPage = new Page<>(params.getPageNo(), params.getPageSize());

        // 构造查询及排序方式
        QueryWrapper<LbAd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ad_type",2);
        queryWrapper.orderBy(true, params.getIsAsc(), params.getIsSortField());

        // 执行查询
        lbAdPage = iLbAdService.getBaseMapper().selectPage(lbAdPage, queryWrapper);

//        List<LbAd> lbAdList = iLbAdService.list(Wrappers.<LbAd>lambdaQuery().eq(LbAd::getAdType,params.getAd_type()));

        return R.ok().setPageResult(lbAdPage);

    }

}
