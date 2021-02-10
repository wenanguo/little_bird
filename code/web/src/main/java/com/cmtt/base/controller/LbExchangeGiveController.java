package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbExchangeOrdersService;
import com.cmtt.base.service.ILbPostService;
import com.cmtt.base.service.ISysUserService;
import com.cmtt.base.utils.SmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import com.cmtt.base.service.ILbExchangeGiveService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 兑换赠送记录表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/api/base/lb-exchange-give")
public class LbExchangeGiveController {

    private final Logger logger = LoggerFactory.getLogger(LbExchangeGiveController.class);

    @Autowired
    public ILbExchangeGiveService lbExchangeGiveService;

    @Autowired
    public ILbExchangeOrdersService lbExchangeOrdersService;

    @Autowired
    public ILbPostService lbPostService;

    @Autowired
    public ISysUserService sysUserService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbExchangeGive lbExchangeGive) {

        try {

// 构建分页类
            IPage<LbExchangeGive> lbExchangeGivePage = new Page<>(lbExchangeGive.getPageNo(), lbExchangeGive.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbExchangeGive> queryWrapper = new QueryWrapper<>(lbExchangeGive);
            queryWrapper.orderBy(true, lbExchangeGive.getIsAsc(), lbExchangeGive.getIsSortField());

            // 执行查询
            lbExchangeGivePage = lbExchangeGiveService.page(lbExchangeGivePage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbExchangeGivePage);


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
    public R add(@RequestBody @Validated({GroupAdd.class}) LbExchangeGive lbExchangeGive, Principal principal) {

        try {

            if (principal != null) {
                // 登录

                // 赠送操作人
                SysUser sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();

                LbPost lbPost = lbPostService.getOne(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getId, lbExchangeGive.getPostId()), false);

                // 赠送目标人
                SysUser giveUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().like(SysUser::getPhone, lbExchangeGive.getPhone()));

                if(giveUser!=null){

                    // 判断是赠送包年还是单点
                    if(lbExchangeGive.getTtype().equals(1)){
                        // 单点
                        String out_trade_no = SmsUtils.genCode(1,20);
                        lbExchangeGive.setOutTradeNo(out_trade_no);
                        lbExchangeGive.setUserId(giveUser.getId());
                        lbExchangeGive.setUserName(giveUser.getNickname());
                        lbExchangeGive.setPhone(giveUser.getPhone());
                        lbExchangeGive.setPostTitle(lbPost.getTitle());
                        lbExchangeGive.setBindUserId(sysUser.getId());
                        lbExchangeGive.setBindUserName(sysUser.getNickname());
                        lbExchangeGive.setUpdateTime(LocalDateTime.now());

                        lbExchangeGiveService.save(lbExchangeGive);



                        // 赠送试读
                        LbExchangeOrders lbExchangeOrders =new LbExchangeOrders();
                        lbExchangeOrders.setOutTradeNo(out_trade_no);
                        lbExchangeOrders.setUserId(giveUser.getId());
                        lbExchangeOrders.setPhone(giveUser.getPhone());
                        lbExchangeOrders.setPostId(lbPost.getId());
                        lbExchangeOrders.setPostTitle(lbPost.getTitle());


                        lbExchangeOrdersService.save(lbExchangeOrders);

                    }else if(lbExchangeGive.getTtype().equals(2)){
                        // 包年
                        String out_trade_no = SmsUtils.genCode(1,20);
                        lbExchangeGive.setOutTradeNo(out_trade_no);
                        lbExchangeGive.setUserId(giveUser.getId());
                        lbExchangeGive.setUserName(giveUser.getNickname());
                        lbExchangeGive.setPhone(giveUser.getPhone());
                        //lbExchangeGive.setPostTitle(lbPost.getTitle());
                        lbExchangeGive.setBindUserId(sysUser.getId());
                        lbExchangeGive.setBindUserName(sysUser.getNickname());
                        lbExchangeGive.setUpdateTime(LocalDateTime.now());

                        lbExchangeGiveService.save(lbExchangeGive);

                        giveUser.setTtype(5);
                        sysUserService.updateById(giveUser);


                    }




                }else{
                    return R.err().setMessage("赠送人手机号码输入错误");
                }






            } else {
                return R.err().setMessage("新增失败，未登录");
            }




            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }


    }


//    /**
//     * 修改
//     */
//    @PutMapping("/edit")
//    @ResponseBody
//    public R edit(@RequestBody @Validated({GroupEdit.class}) LbExchangeGive lbExchangeGive) {
//
//
//        try {
//
//            lbExchangeGiveService.updateById(lbExchangeGive);
//
//            return R.ok().setMessage("修改成功");
//
//        } catch (Exception e) {
//            logger.warn(e.getMessage());
//
//            return R.err().setMessage("修改失败");
//        }
//    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public R delete(@RequestBody @Validated({GroupDelete.class}) LbExchangeGive lbExchangeGive) {

        try {

            lbExchangeGive = lbExchangeGiveService.getOne(Wrappers.<LbExchangeGive>lambdaQuery().eq(LbExchangeGive::getId, lbExchangeGive.getId()), false);

            if(lbExchangeGive.getTtype().equals(1)){

                // 删除点播
                lbExchangeOrdersService.remove(Wrappers.<LbExchangeOrders>lambdaQuery().eq(LbExchangeOrders::getOutTradeNo,lbExchangeGive.getOutTradeNo()));

            }else if(lbExchangeGive.getTtype().equals(2)){
                // 删除包年
                sysUserService.update(Wrappers.<SysUser>lambdaUpdate().set(SysUser::getTtype,1).like(SysUser::getPhone, lbExchangeGive.getPhone()));
            }

            // 删除赠阅记录
            lbExchangeGiveService.removeById(lbExchangeGive.getId());

            return R.ok().setMessage("删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("删除失败");
        }
    }

//
//    /**
//     * 删除
//     */
//    @DeleteMapping("/batchDelete")
//    @ResponseBody
//    public R batchDelete(@RequestBody List<Integer> ids) {
//        try {
//
//            lbExchangeGiveService.removeByIds(ids);
//
//            return R.ok().setMessage("批量删除成功");
//        } catch (Exception e) {
//            logger.warn(e.getMessage());
//
//            return R.err().setMessage("批量删除失败");
//        }
//    }


}
