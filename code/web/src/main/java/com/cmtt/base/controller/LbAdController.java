package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.LbAd;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbAdService;
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

    private final Logger logger = LoggerFactory.getLogger(LbAdController.class);

    /**
     * 获取启动广告
     */
    @PostMapping("get_start_ad")
    @ResponseBody
    @ApiOperation("获取启动广告")
    public R getStartAd(){

        LbAd lbAd = lbAdService.getOne(Wrappers.<LbAd>lambdaQuery().eq(LbAd::getAdType,1));

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
        lbAdPage = lbAdService.getBaseMapper().selectPage(lbAdPage, queryWrapper);

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
    public R delete(@RequestBody @Validated({GroupDelete.class})LbAd lbAd) {

        try {

            lbAdService.removeById(lbAd.getId());

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

            lbAdService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


}
