package com.cmtt.base.controller;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.entity.LbExchangeOrders;
import com.cmtt.base.entity.LbOrders;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.entity.validated.GroupLogin;
import com.cmtt.base.service.ILbExchangeOrdersService;
import com.cmtt.base.service.ILbOrdersService;
import com.cmtt.base.service.ISysUserService;
import com.cmtt.base.utils.RC;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Autowired
    public ILbExchangeOrdersService lbExchangeOrdersService;

    private final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    /**
     * 获取用户信息
     */
    @PostMapping("/info")
    @ResponseBody
    @ApiOperation("获取用户信息")
    public R info(Principal principal) {

        try {

            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            // 获取当前用户信息

            Map<String,Object> map=new HashMap<>();

            map.put("id",sysUser.getId());
            map.put("username",sysUser.getUsername());
            map.put("nickname",sysUser.getNickname());
            map.put("orgId",sysUser.getOrgId());
            map.put("token",sysUser.getToken());
            map.put("phone",sysUser.getPhone());
            map.put("memo",sysUser.getMemo());
            map.put("sex",sysUser.getSex());
            map.put("email",sysUser.getEmail());
            map.put("icon",sysUser.getIcon());
            map.put("ttype",sysUser.getTtype());
            map.put("wxId",sysUser.getWxId());
            map.put("qqId",sysUser.getQqId());
            map.put("wbId",sysUser.getWbId());
            map.put("last_login",sysUser.getLast_login());
            map.put("status",sysUser.getStatus());
            map.put("updateTime",sysUser.getUpdateTime());
            map.put("createTime",sysUser.getCreateTime());


            // 判断用户是否已经包年付费

            Integer PayOneCount=0;

            // 查询是否包年
            LbOrders lbOrders=lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery()
                    .eq(LbOrders::getPhone, sysUser.getPhone())
                    .eq(LbOrders::getStatus, 203)
                    .eq(LbOrders::getTtype,2)
                    .eq(LbOrders::getTradeStatus, "TRADE_SUCCESS")
            );

            if(lbOrders!=null){

                map.put("isPayYear",true);
                map.put("PayYearInvalid",lbOrders.getGmtPayment().plus(1, ChronoUnit.YEARS));

            }else {
                map.put("isPayYear",false);
                map.put("PayYearInvalid",null);
            }

            // 查询是否点播

            List<LbExchangeOrders> exchangeOrdersList = lbExchangeOrdersService.list(Wrappers.<LbExchangeOrders>lambdaQuery()
                    .eq(LbExchangeOrders::getPhone, sysUser.getPhone())
                    .eq(LbExchangeOrders::getStatus, RC.B_NORMAL.code())
            );



                // 计算剩余兑换数量

                List<LbOrders> lbOrdersList = lbOrdersService.list(Wrappers.<LbOrders>lambdaQuery()
                        .eq(LbOrders::getPhone, sysUser.getPhone())
                        .eq(LbOrders::getStatus, RC.PAY_YES.code())
                        .eq(LbOrders::getTtype, 1)
                        .eq(LbOrders::getTradeStatus, "TRADE_SUCCESS")
                );

                PayOneCount=(lbOrdersList.size()*3)-exchangeOrdersList.size();

                if(PayOneCount<0) PayOneCount=0;

                map.put("PayOneCount",PayOneCount);


            return R.ok().setResult(map);

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("获取失败");
        }

    }

}
