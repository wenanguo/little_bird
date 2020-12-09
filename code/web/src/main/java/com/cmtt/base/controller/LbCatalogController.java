package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.GetCatalogPostInputParam;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbCatalogService;
import com.cmtt.base.service.ILbPostService;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

    private final Logger logger = LoggerFactory.getLogger(LbCatalogController.class);

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


    /**
     * 获取所有列表
     */
    @GetMapping("/list_all")
    @ResponseBody
    public R listAll() {

        try {

            // 执行查询
            List<LbCatalog> list = lbCatalogService.list(Wrappers.<LbCatalog>lambdaQuery().eq(LbCatalog::getStatus, RC.B_NORMAL.code()));

            // 设置返回数据
            return R.ok().setResult(list);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbCatalog lbCatalog) {

        try {

// 构建分页类
            IPage<LbCatalog> lbCatalogPage = new Page<>(lbCatalog.getPageNo(), lbCatalog.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbCatalog> queryWrapper = new QueryWrapper<>(lbCatalog);
            queryWrapper.orderBy(true, lbCatalog.getIsAsc(), lbCatalog.getIsSortField());

            // 执行查询
            lbCatalogPage = lbCatalogService.getBaseMapper().selectPage(lbCatalogPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbCatalogPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }


    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody @Validated({GroupAdd.class})LbCatalog lbCatalog) {

        try {
            lbCatalogService.save(lbCatalog);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }


    }


    /**
     * 修改
     */
    @PutMapping("/edit")
    @ResponseBody
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbCatalog lbCatalog) {


        try {

            lbCatalogService.updateById(lbCatalog);

            return R.ok().setMessage("修改成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("修改失败");
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public R delete(@RequestBody @Validated({GroupDelete.class})LbCatalog lbCatalog) {

        try {

            lbCatalogService.removeById(lbCatalog.getId());

            return R.ok().setMessage("删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("删除失败");
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/batchDelete")
    @ResponseBody
    public R batchDelete(@RequestBody List<Integer> ids) {
        try {

            lbCatalogService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }




}
