package com.cmtt.base.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.entity.LbAppVersion;
import com.cmtt.base.entity.LbProtocol;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUserSession;
import com.cmtt.base.service.ILbAppVersionService;
import com.cmtt.base.service.ILbProtocolService;
import com.cmtt.base.service.ISysUserSessionService;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


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

    @Autowired
    private ILbAppVersionService lbAppVersionService;

    private final Logger logger = LoggerFactory.getLogger(LbPageController.class);


    @Autowired
    private ISysUserSessionService sysUserSessionService;

    @Autowired
    private ILbProtocolService lbProtocolService;



    // 静态页面跳转

    /**
     * 用户协议
     */
    @GetMapping("user_agreement")
    @ApiOperation("用户协议")
    public ModelAndView userAgreement()  {
        ModelAndView mv = new ModelAndView();
        LbProtocol lbProtocol = lbProtocolService.getOne(Wrappers.<LbProtocol>lambdaQuery()
                .eq(LbProtocol::getId, 2)
                .eq(LbProtocol::getStatus,RC.B_NORMAL.code())
                .orderByDesc(LbProtocol::getId)
        );

        mv.addObject("lbProtocol", lbProtocol);

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

        LbProtocol lbProtocol = lbProtocolService.getOne(Wrappers.<LbProtocol>lambdaQuery()
                .eq(LbProtocol::getId, 1)
                .eq(LbProtocol::getStatus,RC.B_NORMAL.code())
                .orderByDesc(LbProtocol::getId)
        );

        mv.addObject("lbProtocol", lbProtocol);
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
        LbProtocol lbProtocol = lbProtocolService.getOne(Wrappers.<LbProtocol>lambdaQuery()
                .eq(LbProtocol::getId, 3)
                .eq(LbProtocol::getStatus,RC.B_NORMAL.code())
                .orderByDesc(LbProtocol::getId)
        );

        mv.addObject("lbProtocol", lbProtocol);

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

    /**
     * app下载
     */
    @GetMapping("app_download")
    @ApiOperation("APP下载")
    public ModelAndView app_download()  {
        ModelAndView mv = new ModelAndView();

        LbAppVersion lbAppVersion = lbAppVersionService.getOne(Wrappers.<LbAppVersion>lambdaQuery().orderByDesc(LbAppVersion::getId),false);

        mv.addObject("android",lbAppVersion.getLinkUrl());
        mv.setViewName("appDownload");
        return mv;

    }


}
