package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.*;
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
    private ILbAdService lbAdService;

    @Autowired
    private ILbSubjectService lbSubjectService;

    @Autowired
    private ILbPeriodicalService lbPeriodicalService;

    @Autowired
    private ILbAuthorService lbAuthorService;

    @Autowired
    private ILbPostService lbPostService;

    private final Logger logger = LoggerFactory.getLogger(LbAdController.class);

    /**
     * 获取启动广告
     */
    @PostMapping("get_start_ad")
    @ResponseBody
    @ApiOperation("获取启动广告")
    public R getStartAd(){

        LbAd lbAd = lbAdService.getOne(Wrappers.<LbAd>lambdaQuery()
                .eq(LbAd::getAdLocation,2)
                .eq(LbAd::getStatus,RC.B_NORMAL.code())
                ,false);

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

//        // 构造查询及排序方式
//        QueryWrapper<LbAd> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("ad_location",1);
//        queryWrapper.orderBy(true, params.getIsAsc(), params.getIsSortField());

        // 执行查询
        lbAdPage = lbAdService.page(lbAdPage, Wrappers.<LbAd>lambdaQuery()
                .eq(LbAd::getAdLocation,1)
                .eq(LbAd::getStatus,RC.B_NORMAL.code())
                .orderByAsc(LbAd::getLbPeriodicalIndex));

//        List<LbAd> lbAdList = iLbAdService.list(Wrappers.<LbAd>lambdaQuery().eq(LbAd::getAdType,params.getAd_type()));

        return R.ok().setPageResult(lbAdPage);

    }




    // 管理后台


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbAd lbAd) {

        try {

            // 构建分页类
            IPage<LbAd> lbAdPage = new Page<>(lbAd.getPageNo(), lbAd.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbAd> queryWrapper = new QueryWrapper<>(lbAd);
            queryWrapper.orderBy(true, lbAd.getIsAsc(), lbAd.getIsSortField());

            // 执行查询
            lbAdPage = lbAdService.getBaseMapper().selectPage(lbAdPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbAdPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class})LbAd lbAd) {

        try {

            if(lbAd.getLbSubjectId()!=null) {
                // 设置栏目
                LbSubject lbSubject = lbSubjectService.getOne(Wrappers.<LbSubject>lambdaQuery().eq(LbSubject::getId, lbAd.getLbSubjectId()), false);
                lbAd.setLbSubjectId(lbSubject.getId());
                lbAd.setLbSubjectTitle(lbSubject.getTitle());
            }

            if(lbAd.getLbPeriodicalId()!=null) {
                // 设置所属期刊
                LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getId, lbAd.getLbPeriodicalId()), false);
                lbAd.setLbPeriodicalId(lbPeriodical.getId());
                lbAd.setLbPeriodicalTitle(lbPeriodical.getTitle());
            }

            if(lbAd.getLbRdPeriodicalId()!=null) {
                // 设置推荐期刊
                LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getId, lbAd.getLbRdPeriodicalId()), false);
                lbAd.setLbRdPeriodicalId(lbPeriodical.getId());
                lbAd.setLbRdPeriodicalTitle(lbPeriodical.getTitle());
            }

            if(lbAd.getLbAuthorId()!=null) {
                // 设置作者
                LbAuthor lbAuthor = lbAuthorService.getOne(Wrappers.<LbAuthor>lambdaQuery().eq(LbAuthor::getId, lbAd.getLbAuthorId()));
                lbAd.setLbAuthorId(lbAuthor.getId());
                lbAd.setLbAuthorName(lbAuthor.getName());
            }

            if(lbAd.getLbPostId()!=null) {
                // 设置文章
                LbPost lbPost = lbPostService.getOne(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getId, lbAd.getLbPostId()));
                lbAd.setLbPostId(lbPost.getId());
                lbAd.setLbPostTitle(lbPost.getTitle());
            }


            lbAdService.save(lbAd);

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
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbAd lbAd) {


        try {

            if(lbAd.getLbSubjectId()!=null) {
                // 设置栏目
                LbSubject lbSubject = lbSubjectService.getOne(Wrappers.<LbSubject>lambdaQuery().eq(LbSubject::getId, lbAd.getLbSubjectId()), false);
                lbAd.setLbSubjectId(lbSubject.getId());
                lbAd.setLbSubjectTitle(lbSubject.getTitle());
            }

            if(lbAd.getLbPeriodicalId()!=null) {
                // 设置首页显示期刊
                LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getId, lbAd.getLbPeriodicalId()), false);
                lbAd.setLbPeriodicalId(lbPeriodical.getId());
                lbAd.setLbPeriodicalTitle(lbPeriodical.getTitle());
            }

            if(lbAd.getLbRdPeriodicalId()!=null) {
                // 设置推荐期刊
                LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getId, lbAd.getLbRdPeriodicalId()), false);
                lbAd.setLbRdPeriodicalId(lbPeriodical.getId());
                lbAd.setLbRdPeriodicalTitle(lbPeriodical.getTitle());
            }

            if(lbAd.getLbAuthorId()!=null) {
                // 设置作者
                LbAuthor lbAuthor = lbAuthorService.getOne(Wrappers.<LbAuthor>lambdaQuery().eq(LbAuthor::getId, lbAd.getLbAuthorId()));
                lbAd.setLbAuthorId(lbAuthor.getId());
                lbAd.setLbAuthorName(lbAuthor.getName());
            }

            if(lbAd.getLbPostId()!=null) {
                // 设置文章
                LbPost lbPost = lbPostService.getOne(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getId, lbAd.getLbPostId()));
                lbAd.setLbPostId(lbPost.getId());
                lbAd.setLbPostTitle(lbPost.getTitle());
            }

            lbAdService.updateById(lbAd);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) GetOneInputParam lbAd) {

        try {

            lbAdService.removeById(lbAd.getId());

            return R.ok().setMessage("删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("删除失败");
        }
    }



}
