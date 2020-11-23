package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.entity.Article;
import com.cmtt.base.entity.LbPeriodical;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.service.ILbPeriodicalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Api(tags = "期刊相关")
public class LbPeriodicalController {


    @Autowired
    private ILbPeriodicalService lbPeriodicalService;


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
     * 主页
     */
    @PostMapping("index_post")
    @ResponseBody
    @ApiOperation("主页文章列表")
    public R index_post(){
        //List<LbPeriodical> lbPeriodicalList = lbPeriodicalService.list(Wrappers.<LbPeriodical>lambdaQuery().eq(LbPeriodical::getStatus, 100));


        List<LbPeriodical> lbPeriodicalList = lbPeriodicalService.getLbPostList();

        return R.ok().setResult(lbPeriodicalList);
    }
}
