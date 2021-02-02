package com.cmtt.base.controllerv2;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.param.GetAuthorPostInputParam;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 栏目表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/v2/")
@Api(tags = "V2")
public class V2LbPostController {

    private final Logger logger = LoggerFactory.getLogger(V2LbPostController.class);

    @Autowired
    private ILbSubjectService lbSubjectService;

    @Autowired
    private ILbAuthorService lbAuthorService;

    @Autowired
    private ILbPostService lbPostService;

    @Autowired
    private ILbCatalogService lbCatalogService;

    @Autowired
    private ILbAdService lbAdService;

    @Autowired
    private ILbPeriodicalService lbPeriodicalService;

    @Autowired
    private ILbOrdersService lbOrdersService;

    /**
     * 主页
     */
    @PostMapping("lb_subject/get_one")
    @ResponseBody
    @ApiOperation("获取单个栏目文章列表(文章)")
    public R list(@RequestBody @Valid GetOneInputParam params){


        LbSubject lbSubject = lbSubjectService.getOne(Wrappers.<LbSubject>lambdaQuery().eq(LbSubject::getId,params.getId()));

        if(lbSubject!=null){
            LbCatalog lbCatalog = lbCatalogService.getOne(Wrappers.<LbCatalog>lambdaQuery().eq(LbCatalog::getId, lbSubject.getCatalogId()));
            lbSubject.setLbCatalog(lbCatalog);

            // 获取统一的文章查询条件
            LambdaQueryWrapper<LbPost> queryWrapper = lbPostService.getCommonPostWrappers(true)
                    .eq(LbPost::getPostSubjectId, lbSubject.getId());

            List<LbPost> lbPosts = lbPostService.list(queryWrapper);

            // 兼容老版本特殊处理
            lbPosts.forEach(lbPost -> {
                if(lbPost.getIsPreview()==2)lbPost.setIsFree(4);
            });

            lbSubject.setLbPostList(lbPosts);
        }


        return R.ok().setResult(lbSubject);
    }


    /**
     * 获取作者及所属文章
     */
    @PostMapping("lb_author/get_one")
    @ResponseBody
    @ApiOperation("获取作者及所属文章（文章）")
    public R getAuthorPost(@RequestBody @Valid GetOneInputParam params){

        LbAuthor lbAuthor = lbAuthorService.getOne(Wrappers.<LbAuthor>lambdaQuery().eq(LbAuthor::getId, params.getId()),false);

        if(lbAuthor!=null){

            // 获取统一的文章查询条件
            LambdaQueryWrapper<LbPost> queryWrapper = lbPostService.getCommonPostWrappers(true)
                    .like(LbPost::getAuthor, lbAuthor.getName());

            List<LbPost> list = lbPostService.list(queryWrapper);

            // 兼容老版本特殊处理
            list.forEach(lbPost -> {
                if(lbPost.getIsPreview()==2)lbPost.setIsFree(4);
            });

            lbAuthor.setLbPostList(list);
            return R.ok().setResult(lbAuthor);
        }else{

            return R.err().setMessage("找不到当前作者数据");
        }


    }

    /**
     * 当期封面
     */
    @PostMapping("lb_periodical/get_one")
    @ResponseBody
    @ApiOperation("单期期刊数据(目录页)")
    public R getOne(@RequestBody @Valid GetOneInputParam params, Principal principal){

        LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getId, params.getId()));

        if(lbPeriodical!=null){
//            List<LbPost> list = lbPostService.list(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getPeriodicalId, lbPeriodical.getId()));
//            lbPeriodical.setLbPostList(list);

            List<LbCatalog> lbCatalogList = lbCatalogService.getV2LbCatalogPostListByPeriodicalId(lbPeriodical.getId());

            // 兼容老版本特殊处理
            lbCatalogList.forEach(lbCatalog -> {
                  lbCatalog.getLbPostList().forEach(lbPost -> {
                    if(lbPost.getIsPreview()==2)lbPost.setIsFree(4);
                });

            });



            Map<String, Object> map = new HashMap();
            map.put("id", lbPeriodical.getId());
            map.put("title", lbPeriodical.getTitle());
            map.put("tcode", lbPeriodical.getTcode());
            map.put("imgUrl", lbPeriodical.getImgUrl());
            map.put("recommend", lbPeriodical.getRecommend());
            map.put("tyear", lbPeriodical.getTyear());
            map.put("lbCatalogList", lbCatalogList);
            map.put("isDownload", lbPeriodical.getIsDownload());

            if(principal!=null){
                // 已登录
                SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
                boolean isPayYear=lbOrdersService.isPayYear(sysUser.getPhone());
                if(isPayYear==true){
                    // 包年
                    map.put("tpdf", lbPeriodical.getTpdf());
                }else{
                    map.put("tpdf", "");
                }
            }else {
                map.put("tpdf", "");
            }

            return R.ok().setResult(map);
        }else{

            return R.err().setMessage("找不当当前期刊数据");
        }

    }



}
