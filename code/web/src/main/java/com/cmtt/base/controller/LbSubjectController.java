package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.cmtt.base.service.ILbAdService;
import com.cmtt.base.service.ILbCatalogService;
import com.cmtt.base.service.ILbPostService;
import com.cmtt.base.service.ILbSubjectService;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 栏目表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/lb_subject")
@Api(tags = "主页相关")
public class LbSubjectController {

    private final Logger logger = LoggerFactory.getLogger(LbSubjectController.class);

    @Autowired
    private ILbSubjectService lbSubjectService;

    @Autowired
    private ILbPostService lbPostService;

    @Autowired
    private ILbCatalogService lbCatalogService;

    @Autowired
    private ILbAdService lbAdService;

    /**
     * 主页
     */
    @PostMapping("/get_one")
    @ResponseBody
    @ApiOperation("获取单个栏目文章列表(文章)")
    public R list(@RequestBody @Valid GetOneInputParam params){

//        // 构建分页类
//        IPage<LbSubject> lbSubjectIPage = new Page<>(params.getPageNo(), params.getPageSize());
//
//        // 构造查询及排序方式
//        QueryWrapper<LbSubject> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderBy(true, params.getIsAsc(), params.getIsSortField());
//
//        // 执行查询
//        lbSubjectIPage = lbSubjectService.getLbSubjectPostList(lbSubjectIPage);
//
//        return R.ok().setPageResult(lbSubjectIPage);


        LbSubject lbSubject = lbSubjectService.getOne(Wrappers.<LbSubject>lambdaQuery().eq(LbSubject::getId,params.getId()));

        if(lbSubject!=null){
            LbCatalog lbCatalog = lbCatalogService.getOne(Wrappers.<LbCatalog>lambdaQuery().eq(LbCatalog::getId, lbSubject.getCatalogId()));
            lbSubject.setLbCatalog(lbCatalog);

            // 获取统一的文章查询条件
            LambdaQueryWrapper<LbPost> queryWrapper = lbPostService.getCommonPostWrappers(false)
                    .eq(LbPost::getPostSubjectId, lbSubject.getId());

            List<LbPost> lbPosts = lbPostService.list(queryWrapper);

            lbSubject.setLbPostList(lbPosts);
        }


        return R.ok().setResult(lbSubject);
    }



    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbSubject lbSubject) {

        try {

            // 构建分页类
            IPage<LbSubject> lbSubjectPage = new Page<>(lbSubject.getPageNo(), lbSubject.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbSubject> queryWrapper = new QueryWrapper<>(lbSubject);
            queryWrapper.orderBy(true, lbSubject.getIsAsc(), lbSubject.getIsSortField());

            // 执行查询
            lbSubjectPage = lbSubjectService.getBaseMapper().selectPage(lbSubjectPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbSubjectPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }


    /**
     * 获取所有列表
     */
    @GetMapping("/list_all")
    @ResponseBody
    public R listAll() {

        try {

            // 执行查询
            List<LbSubject> list = lbSubjectService.list(Wrappers.<LbSubject>lambdaQuery().eq(LbSubject::getStatus, RC.B_NORMAL.code()));

            // 设置返回数据
            return R.ok().setResult(list);


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
    public R add(@RequestBody @Validated({GroupAdd.class})LbSubject lbSubject) {

        try {
            lbSubjectService.save(lbSubject);

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
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbSubject lbSubject) {


        try {

            lbSubjectService.updateById(lbSubject);

            // 修改文章表冗余字段
            lbPostService.update(Wrappers.<LbPost>lambdaUpdate().eq(LbPost::getPostSubjectId, lbSubject.getId()).set(LbPost::getPostSubject, lbSubject.getTitle()));

            // 修改广告表冗余字段
            lbAdService.update(Wrappers.<LbAd>lambdaUpdate().eq(LbAd::getLbSubjectId, lbSubject.getId()).set(LbAd::getLbSubjectTitle, lbSubject.getTitle()));



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
    public R delete(@RequestBody @Validated({GroupDelete.class})GetOneInputParam lbSubject) {

        try {

            // 修改文章表冗余字段
            List<LbPost> lbPostList = lbPostService.list(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getPostSubjectId, lbSubject.getId()));

            if(lbPostList.size()==0){
                lbSubjectService.removeById(lbSubject.getId());

                return R.ok().setMessage("删除成功");
            }else{
                return R.err().setMessage("当前栏目下还有文章，不能删除");
            }



        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("删除失败");
        }
    }

}
