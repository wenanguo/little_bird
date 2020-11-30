package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.controller.param.PhoneLoginInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbCatalogService;
import com.cmtt.base.service.ILbPeriodicalService;
import com.cmtt.base.service.ILbPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 期刊表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
@RestController
@RequestMapping("/api/lb_periodical")
@Api(tags = "主页相关")
public class LbPeriodicalController {

    private final Logger logger = LoggerFactory.getLogger(LbPeriodicalController.class);

    @Autowired
    private ILbPeriodicalService lbPeriodicalService;

    @Autowired
    private ILbCatalogService lbCatalogService;

    /**
     * 当期封面
     */
    @PostMapping("current")
    @ResponseBody
    @ApiOperation("当期封面信息")
    public R current(){
        LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getRecommend, 1));

        return R.ok().setResult(lbPeriodical);
    }


    /**
     * 当期封面
     */
    @PostMapping("get_one")
    @ResponseBody
    @ApiOperation("单期期刊数据")
    public R getOne(@RequestBody @Valid GetOneInputParam params){

        LbPeriodical lbPeriodical = lbPeriodicalService.getOne(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getId, params.getId()));

        if(lbPeriodical!=null){
//            List<LbPost> list = lbPostService.list(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getPeriodicalId, lbPeriodical.getId()));
//            lbPeriodical.setLbPostList(list);

            List<LbCatalog> lbCatalogList = lbCatalogService.getLbCatalogPostListByPeriodicalId(lbPeriodical.getId());

            Map<String, Object> map = new HashMap();
            map.put("id", lbPeriodical.getId());
            map.put("title", lbPeriodical.getTitle());
            map.put("tcode", lbPeriodical.getTcode());
            map.put("imgUrl", lbPeriodical.getImgUrl());
            map.put("recommend", lbPeriodical.getRecommend());
            map.put("tyear", lbPeriodical.getTyear());
            map.put("lbCatalogList", lbCatalogList);

            return R.ok().setResult(map);
        }else{

            return R.err().setMessage("找不当当前期刊数据");
        }




    }


    /**
     * 主页
     */
    @PostMapping("index_post")
    @ResponseBody
    @ApiOperation("主页文章列表")
    public R index_post(@RequestBody @Valid LbPeriodical lbPeriodical){
        //List<LbPeriodical> lbPeriodicalList = lbPeriodicalService.list(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getStatus, 100));

        // 构建分页类
        IPage<LbPeriodical> lbPeriodicalPage = new Page<>(lbPeriodical.getPageNo(), lbPeriodical.getPageSize());

        // 构造查询及排序方式
        QueryWrapper<LbPeriodical> queryWrapper = new QueryWrapper<>(lbPeriodical);
        queryWrapper.orderBy(true, lbPeriodical.getIsAsc(), lbPeriodical.getIsSortField());

        // 执行查询
        //lbPeriodicalPage = lbPeriodicalService.getBaseMapper().selectPage(lbPeriodicalPage, queryWrapper);



        lbPeriodicalPage = lbPeriodicalService.getLbPostList(lbPeriodicalPage);
        R r=R.ok();
        r.setPageResult(lbPeriodicalPage);
        return r;
    }


    /**
     * 书架列表
     */
    @PostMapping("bookrack")
    @ResponseBody
    @ApiOperation("书架列表")
    public R bookrack(){



        List<LbPeriodical> lbPeriodicalList = lbPeriodicalService.list(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getStatus, 100));



        Map<String, List<LbPeriodical>> map = new HashMap();



        lbPeriodicalList.forEach((item)->{

            String tyear=item.getTyear();

            List<LbPeriodical> tyearList=map.get(tyear);
            if(tyearList==null){
                tyearList=new ArrayList<LbPeriodical>();
                map.put(tyear,tyearList);
            }
            tyearList.add(item);
        });


        List<Map<String, Object>> retList=new ArrayList<>();

        for(String key : map.keySet()){

            Map<String, Object> retMap = new HashMap();
            retMap.put("year",key);
            retMap.put("dataList",map.get(key));
            retList.add(retMap);
        }


        return R.ok().setResult(retList);

//
//        // 构建分页类
//        IPage<LbPeriodical> lbPeriodicalPage = new Page<>(params.getPageNo(), params.getPageSize());
//
//        // 构造查询及排序方式
//        QueryWrapper<LbPeriodical> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderBy(true, params.getIsAsc(), params.getIsSortField());
//
//        // 执行查询
//        lbPeriodicalPage = lbPeriodicalService.getBaseMapper().selectPage(lbPeriodicalPage, queryWrapper);
//
//
//
//        //lbPeriodicalPage = lbPeriodicalService.getLbPostList(lbPeriodicalPage);
//        R r=R.ok().setPageResult(lbPeriodicalPage);
//        return r;
    }






    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbPeriodical lbPeriodical) {

        try {

// 构建分页类
            IPage<LbPeriodical> lbPeriodicalPage = new Page<>(lbPeriodical.getPageNo(), lbPeriodical.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbPeriodical> queryWrapper = new QueryWrapper<>(lbPeriodical);
            queryWrapper.orderBy(true, lbPeriodical.getIsAsc(), lbPeriodical.getIsSortField());

            // 执行查询
            lbPeriodicalPage = lbPeriodicalService.getBaseMapper().selectPage(lbPeriodicalPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbPeriodicalPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class})LbPeriodical lbPeriodical) {

        try {
            lbPeriodicalService.save(lbPeriodical);

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
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbPeriodical lbPeriodical) {


        try {

            lbPeriodicalService.updateById(lbPeriodical);

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
    public R delete(@RequestBody @Validated({GroupDelete.class})LbPeriodical lbPeriodical) {

        try {

            lbPeriodicalService.removeById(lbPeriodical.getId());

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

            lbPeriodicalService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }




}