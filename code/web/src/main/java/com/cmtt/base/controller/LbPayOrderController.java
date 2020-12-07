package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cmtt.base.entity.LbPayOrder;
import com.cmtt.base.service.ILbPayOrderService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 支付订单表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/api/base/lb-pay-order")
public class LbPayOrderController {

    private final Logger logger = LoggerFactory.getLogger(LbPayOrderController.class);

    @Autowired
    public ILbPayOrderService lbPayOrderService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbPayOrder lbPayOrder) {

        try {

            // 构建分页类
            IPage<LbPayOrder> lbPayOrderPage = new Page<>(lbPayOrder.getPageNo(), lbPayOrder.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbPayOrder> queryWrapper = new QueryWrapper<>(lbPayOrder);
            queryWrapper.orderBy(true, lbPayOrder.getIsAsc(), lbPayOrder.getIsSortField());

            // 执行查询
            lbPayOrderPage = lbPayOrderService.getBaseMapper().selectPage(lbPayOrderPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbPayOrderPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }


}
