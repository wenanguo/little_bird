package com.cmtt.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * <p>
 * 页面 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
@RestController
@RequestMapping("/page/")
@Api(tags = "静态页面")
public class LbPageController {

    private final Logger logger = LoggerFactory.getLogger(LbPageController.class);


    // 静态页面跳转

    /**
     * 用户协议
     */
    @GetMapping("user_agreement")
    @ApiOperation("用户协议")
    public ModelAndView userAgreement()  {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("userAgreement");
        return mv;

    }

    /**
     * 隐私协议
     */
    @GetMapping("privacy_agreement")
    @ApiOperation("隐私协议")
    public ModelAndView privacyAgreement()  {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("privacyAgreement");
        return mv;

    }


    /**
     * 关于小鸟
     */
    @GetMapping("about")
    @ApiOperation("关于小鸟")
    public ModelAndView about()  {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("about");
        return mv;

    }

    /**
     * 下载
     */
    @GetMapping("download")
    @ApiOperation("下载")
    public ModelAndView download()  {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("download");
        return mv;

    }


}
