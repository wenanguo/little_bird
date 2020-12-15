package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.SysUserOrders;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.cmtt.base.entity.LbOrders;
import com.cmtt.base.service.ILbOrdersService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品订单表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/api/lb_orders")
public class LbOrdersController {

private final Logger logger = LoggerFactory.getLogger(LbOrdersController.class);

@Autowired
public ILbOrdersService lbOrdersService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(LbOrders lbOrders) {

try {

// 构建分页类
IPage<LbOrders> lbOrdersPage = new Page<>(lbOrders.getPageNo(), lbOrders.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<LbOrders> queryWrapper = new QueryWrapper<>(lbOrders);
        queryWrapper.orderBy(true, lbOrders.getIsAsc(), lbOrders.getIsSortField());

        // 执行查询
        lbOrdersPage = lbOrdersService.getBaseMapper().selectPage(lbOrdersPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(lbOrdersPage);


        } catch (Exception e) {

        logger.warn(e.getMessage());

        return R.err().setMessage("系统错误");
        }
        }



    /**
     * 分页获取列表
     */
    @GetMapping("/user_order_list")
    @ResponseBody
    public R userOrderList(SysUserOrders lbOrders) {

        try {

            // 构建分页类
            IPage<SysUserOrders> lbOrdersPage = new Page<>(lbOrders.getPageNo(), lbOrders.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SysUserOrders> queryWrapper = new QueryWrapper<>(lbOrders);
            queryWrapper.orderBy(true, lbOrders.getIsAsc(), lbOrders.getIsSortField());

            // 执行查询
            lbOrdersPage = lbOrdersService.getSysUserOrdersList(lbOrdersPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbOrdersPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})LbOrders lbOrders) {

        try {
        lbOrdersService.save(lbOrders);

        return R.ok().setMessage("新增成功");

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})LbOrders lbOrders) {


        try {

        lbOrdersService.updateById(lbOrders);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})LbOrders lbOrders) {

        try {

        lbOrdersService.removeById(lbOrders.getId());

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

            lbOrdersService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }



}
