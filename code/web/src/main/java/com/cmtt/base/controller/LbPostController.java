package com.cmtt.base.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.controller.param.LbPostInputParam;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.*;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

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
    private ILbAuthorService lbAuthorService;

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


        // 判断用户是否已经付费，包年，或者单篇购买
        boolean isPay=false;

        if(!isPay){
            // 未付费，隐藏付费内容
            lbPost.setFeeContent("");
        }


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
        lbPost.setLbAuthorList(lbPost.getLbAuthorList());


        return R.ok().setResult(lbPost);
    }




    /**
     * 文章详情
     */
    @PostMapping("get_fee_content")
    @ResponseBody
    @ApiOperation("获取付费内容")
    public R get_fee_content(@RequestBody @Valid GetOneInputParam params) throws IOException, TemplateException {

        // 判断用户是否已经付费，包年，或者单篇购买


        // 执行查询
        LbPost lbPost = lbPostService.getOne(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getId, params.getId()));

        return R.ok().setResult(lbPost);
    }


    /**
     * 文章详情
     */
    @GetMapping("share")
    @ApiOperation("文章详情分享")
    public ModelAndView share(@Valid GetOneInputParam params)  {

        // 执行查询
        LbPost lbPost = lbPostService.getOne(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getId, params.getId()));

        // 分享内容，隐藏付费内容
        lbPost.setFeeContent("");

        lbPost.setLbAuthorList(lbPost.getLbAuthorList());

        ModelAndView mv = new ModelAndView();
        mv.addObject("lbPost", lbPost);
        mv.setViewName("shareAreicle");
        return mv;

    }


    /**
     * 文章详情
     */
    @GetMapping("preview")
    @ApiOperation("文章详情预览")
    public ModelAndView preview(@Valid GetOneInputParam params)  {

        // 执行查询
        LbPost lbPost = lbPostService.getOne(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getId, params.getId()));

        // 分享内容，隐藏付费内容
        lbPost.setFeeContent("");

        // 获取作者列表
        List<LbAuthor> lbAuthorList = lbAuthorService.list(new QueryWrapper<LbAuthor>().in("id", lbPost.getLbAuthorIdsList()));


        ModelAndView mv = new ModelAndView();
        mv.addObject("lbPost", lbPost);
        mv.addObject("lbAuthorList",lbAuthorList);
        mv.setViewName("articleDetails");
        return mv;

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
    public R add(@RequestBody @Validated({GroupAdd.class}) LbPostInputParam params) {

        try {

            LbPost lbPost=new LbPost();
            BeanUtils.copyProperties(params,lbPost);

            //条件构造器in上手使用
//            QueryWrapper<LbAuthor> qw = new QueryWrapper<LbAuthor>().in("id", params.getLbAuthorIdsList());
//            qw.in("id", params.getLbAuthorIdsList());
            List<LbAuthor> lbAuthorList = lbAuthorService.list(new QueryWrapper<LbAuthor>().in("id", params.getLbAuthorIdsList()));

            List<LbAuthorVo> lbAuthorVoList= new ArrayList<>();
            lbAuthorList.forEach(s -> lbAuthorVoList.add(new LbAuthorVo().setId(s.getId()).setName(s.getName())));


            lbPost.setAuthor(JSON.toJSONString(lbAuthorVoList));


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
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbPostInputParam params) {


        try {

            LbPost lbPost=new LbPost();
            BeanUtils.copyProperties(params,lbPost);

            LbCatalog lbCatalog = lbCatalogService.getOne(Wrappers.<LbCatalog>lambdaQuery().eq(LbCatalog::getId, lbPost.getPostCatalogId()));
            lbPost.setPostCatalog(lbCatalog.getTitle());
            lbPost.setTcolor(lbCatalog.getTcolor());

            LbSubject lbSubject = lbSubjectService.getOne(Wrappers.<LbSubject>lambdaQuery().eq(LbSubject::getId, lbPost.getPostSubjectId()));
            lbPost.setPostSubject(lbSubject.getTitle());

            LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getId, lbPost.getPeriodicalId()));
            lbPost.setPeriodicalTitle(lbPeriodical.getTitle());


            // 修改作者
            List<LbAuthor> lbAuthorList = lbAuthorService.list(new QueryWrapper<LbAuthor>().in("id", params.getLbAuthorIdsList()));

            List<LbAuthorVo> lbAuthorVoList= new ArrayList<>();
            lbAuthorList.forEach(s -> lbAuthorVoList.add(new LbAuthorVo().setId(s.getId()).setName(s.getName())));

            lbPost.setAuthor(JSON.toJSONString(lbAuthorVoList));

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
