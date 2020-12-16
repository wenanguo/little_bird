package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbOrdersService;
import com.cmtt.base.service.ILbPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import com.cmtt.base.service.ILbExchangeOrdersService;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 订单商品表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/api/lb_exchange_orders")
@Api(tags = "支付相关")
public class LbExchangeOrdersController {

    private final Logger logger = LoggerFactory.getLogger(LbExchangeOrdersController.class);

    @Autowired
    public ILbExchangeOrdersService lbExchangeOrdersService;

    @Autowired
    public ILbPostService lbPostService;

    @Autowired
    private ILbOrdersService lbOrdersService;

    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbExchangeOrders lbExchangeOrders) {

        try {

            // 构建分页类
            IPage<LbExchangeOrders> lbExchangeOrdersPage = new Page<>(lbExchangeOrders.getPageNo(), lbExchangeOrders.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbExchangeOrders> queryWrapper = new QueryWrapper<>(lbExchangeOrders);
            queryWrapper.orderBy(true, lbExchangeOrders.getIsAsc(), lbExchangeOrders.getIsSortField());

            // 执行查询
            lbExchangeOrdersPage = lbExchangeOrdersService.getBaseMapper().selectPage(lbExchangeOrdersPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbExchangeOrdersPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class}) LbExchangeOrders lbExchangeOrders) {

        try {
            lbExchangeOrdersService.save(lbExchangeOrders);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }


    }


    /**
     * 兑换
     */
    @PostMapping("/exchange")
    @ResponseBody
    @ApiOperation("文章兑换")
    public R exchange(@RequestBody @Valid GetOneInputParam params, Principal principal) {

        try {
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();


            // 获取当前用户兑换数量
            List<LbExchangeOrders> exchangeOrdersList = lbExchangeOrdersService.list(Wrappers.<LbExchangeOrders>lambdaQuery()
                    .eq(LbExchangeOrders::getPhone, sysUser.getPhone())
                    .eq(LbExchangeOrders::getStatus, 100)
            );

            // 获取购买成功订单数量
            List<LbOrders> lbOrdersList = lbOrdersService.list(Wrappers.<LbOrders>lambdaQuery()
                    .eq(LbOrders::getPhone, sysUser.getPhone())
                    .eq(LbOrders::getStatus, 203)
                    .eq(LbOrders::getTtype, 2)
                    .eq(LbOrders::getTradeStatus, "TRADE_SUCCESS")
            );

            Integer PayOneCount=(lbOrdersList.size()*3)-exchangeOrdersList.size();

            if(PayOneCount>0){
                LbExchangeOrders lbExchangeOrders =new LbExchangeOrders();
                lbExchangeOrders.setUserId(sysUser.getId());
                lbExchangeOrders.setPhone(sysUser.getPhone());

                LbPost lbPost = lbPostService.getOne(Wrappers.<LbPost>lambdaQuery().eq(LbPost::getId, params.getId()));
                lbExchangeOrders.setPostId(lbPost.getId());
                lbExchangeOrders.setPostTitle(lbPost.getTitle());


                lbExchangeOrdersService.save(lbExchangeOrders);

                return R.ok().setMessage("兑换成功");
            }else{
                return R.err().setMessage("兑换失败，可兑换次数不够，请再次购买");
            }



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
    public R edit(@RequestBody @Validated({GroupEdit.class}) LbExchangeOrders lbExchangeOrders) {


        try {

            lbExchangeOrdersService.updateById(lbExchangeOrders);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) LbExchangeOrders lbExchangeOrders) {

        try {

            lbExchangeOrdersService.removeById(lbExchangeOrders.getId());

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

            lbExchangeOrdersService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


}
