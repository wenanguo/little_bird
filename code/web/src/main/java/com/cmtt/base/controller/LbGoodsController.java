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

import com.cmtt.base.entity.LbGoods;
import com.cmtt.base.service.ILbGoodsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/api/base/lb_goods")
public class LbGoodsController {

private final Logger logger = LoggerFactory.getLogger(LbGoodsController.class);

@Autowired
public ILbGoodsService lbGoodsService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(LbGoods lbGoods) {

try {

// 构建分页类
IPage<LbGoods> lbGoodsPage = new Page<>(lbGoods.getPageNo(), lbGoods.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<LbGoods> queryWrapper = new QueryWrapper<>(lbGoods);
        queryWrapper.orderBy(true, lbGoods.getIsAsc(), lbGoods.getIsSortField());

        // 执行查询
        lbGoodsPage = lbGoodsService.getBaseMapper().selectPage(lbGoodsPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(lbGoodsPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})LbGoods lbGoods) {

        try {
        lbGoodsService.save(lbGoods);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})LbGoods lbGoods) {


        try {

        lbGoodsService.updateById(lbGoods);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})LbGoods lbGoods) {

        try {

        lbGoodsService.removeById(lbGoods.getId());

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

            lbGoodsService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }



}
