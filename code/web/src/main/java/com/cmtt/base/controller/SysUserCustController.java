package com.cmtt.base.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.entity.LbOrders;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.service.ILbOrdersService;
import com.cmtt.base.service.ISysUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


/**
 * <p>
 * 自定义用户表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/sys_user")
@Api(tags = "用户相关")
public class SysUserCustController {


    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ILbOrdersService lbOrdersService;

    private final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    /**
     * 获取用户信息
     */
    @PostMapping("/info")
    @ResponseBody
    public R info(Principal principal) {

        try {

            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            // 获取当前用户信息

            LbOrders lbOrders=lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery()
                    .eq(LbOrders::getPhone, sysUser.getPhone())
                    .eq(LbOrders::getStatus, 203)
                    .eq(LbOrders::getTtype,2)
                    .eq(LbOrders::getTradeStatus, "TRADE_SUCCESS")
            );

            if(lbOrders!=null){

            }

            return R.ok().setResult(sysUser);

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("获取失败");
        }

    }

}
