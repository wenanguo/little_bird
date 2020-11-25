package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.controller.param.AppUpdateInputParam;
import com.cmtt.base.controller.param.GetAuthorPostInputParam;
import com.cmtt.base.entity.LbAppVersion;
import com.cmtt.base.entity.LbAuthor;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.R;
import com.cmtt.base.service.ILbAppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/lb_app_version")
@Api(tags = "主页相关")
public class LbAppVersionController {

    @Autowired
    private ILbAppVersionService lbAppVersionService;

    /**
     * 获取作者及所属文章
     */
    @PostMapping("app_update")
    @ResponseBody
    @ApiOperation("app更新")
    public R appUpdate(@RequestBody @Valid AppUpdateInputParam params){

        LbAppVersion lbAppVersion = lbAppVersionService.getOne(Wrappers.<LbAppVersion>lambdaQuery().orderByDesc(LbAppVersion::getId));




        if(lbAppVersion!=null ) {

            // 判断当前版本是否小于最新版本，如果是，返回最新版本

            return R.ok().setResult(lbAppVersion);
        }else{
            return R.err();
        }




    }




}
