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
import com.cmtt.base.service.ILbCatalogService;
import com.cmtt.base.service.ILbPeriodicalService;
import com.cmtt.base.service.ILbPostService;
import com.cmtt.base.service.ILbSubjectService;
import com.cmtt.base.service.impl.LbPostServiceImpl;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
@RestController
@RequestMapping("/api/lb_post")
@Api(tags = "主页相关")
public class LbPostController {

    private final Logger logger = LoggerFactory.getLogger(LbPostController.class);

    @Autowired
    private ILbPostService lbPostService;

    @Autowired
    private ILbCatalogService lbCatalogService;

    @Autowired
    private ILbSubjectService lbSubjectService;

    @Autowired
    private ILbPeriodicalService lbPeriodicalService;

    @Autowired
    private FreeMarkerConfigurer configurer;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 主页
     */
    @PostMapping("search")
    @ResponseBody
    @ApiOperation("搜索")
    public R search(@RequestBody @Valid PageInputParam params){

        // 构建分页类
        IPage<LbPost> lbPostPage = new Page<>(params.getPageNo(), params.getPageSize());

        // 构造查询及排序方式
        QueryWrapper<LbPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",params.getKeyString());
        queryWrapper.orderBy(true, params.getIsAsc(), params.getIsSortField());

        // 执行查询
        lbPostPage = lbPostService.getBaseMapper().selectPage(lbPostPage, queryWrapper);


        R r=R.ok();
        r.setPageResult(lbPostPage);
        return r;
    }



    /**
     * 文章详情
     */
    @PostMapping("detail")
    @ResponseBody
    @ApiOperation("文章详情")
    public R detail(@RequestBody @Valid GetOneInputParam params) throws IOException, TemplateException {

        // 执行查询
        LbPost lbPost = lbPostService.getOne(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getId, params.getId()));



//        Map<String,Object> map = new HashMap<>();
//        map.put("lbPost",lbPost);
//        Template template = configurer.getConfiguration().getTemplate("articleDetails.html");
//        String resStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//
//        lbPost.setContent(resStr);





        // 改用thymeleaf

        Context context=new Context();
        context.setVariable("lbPost",lbPost);
        String result=templateEngine.process("articleDetails",context);
        //System.out.println(result);
        lbPost.setContent(result);



        return R.ok().setResult(lbPost);
    }



    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbPost lbPost) {

        try {

// 构建分页类
            IPage<LbPost> lbPostPage = new Page<>(lbPost.getPageNo(), lbPost.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbPost> queryWrapper = new QueryWrapper<>(lbPost);
            queryWrapper.orderBy(true, lbPost.getIsAsc(), lbPost.getIsSortField());

            // 执行查询
            lbPostPage = lbPostService.getBaseMapper().selectPage(lbPostPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbPostPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class})LbPost lbPost) {

        try {
            lbPostService.save(lbPost);

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
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbPost lbPost) {


        try {

            LbCatalog lbCatalog = lbCatalogService.getOne(Wrappers.<LbCatalog>lambdaQuery().eq(LbCatalog::getId, lbPost.getPostCatalogId()));
            lbPost.setPostCatalog(lbCatalog.getTitle());
            lbPost.setTcolor(lbCatalog.getTcolor());

            LbSubject lbSubject = lbSubjectService.getOne(Wrappers.<LbSubject>lambdaQuery().eq(LbSubject::getId, lbPost.getPostSubjectId()));
            lbPost.setPostSubject(lbSubject.getTitle());

            LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getId, lbPost.getPeriodicalId()));
            lbPost.setPeriodicalTitle(lbPeriodical.getTitle());

            lbPostService.updateById(lbPost);

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
    public R delete(@RequestBody @Validated({GroupDelete.class})LbPost lbPost) {

        try {

            lbPostService.removeById(lbPost.getId());

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

            lbPostService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }



}
