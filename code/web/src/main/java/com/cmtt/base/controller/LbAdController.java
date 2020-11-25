package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.controller.param.GetAdInputParam;
import com.cmtt.base.controller.param.GetAuthorPostInputParam;
import com.cmtt.base.entity.LbAd;
import com.cmtt.base.entity.LbAuthor;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.R;
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
    @PostMapping("getAdt")
    @ResponseBody
    @ApiOperation("获取广告")
    public R getAd(@RequestBody @Valid GetAdInputParam params){

        List<LbAd> lbAdList = iLbAdService.list(Wrappers.<LbAd>lambdaQuery().eq(LbAd::getAdType,params.getAd_type()));


        return R.ok().setResult(lbAdList);



    }

}
