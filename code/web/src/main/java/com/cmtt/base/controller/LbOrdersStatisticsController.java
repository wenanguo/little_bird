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

import com.cmtt.base.entity.LbOrdersStatistics;
import com.cmtt.base.service.ILbOrdersStatisticsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单统计表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-01
 */
@RestController
@RequestMapping("/api/base/lb-orders-statistics")
public class LbOrdersStatisticsController {

private final Logger logger = LoggerFactory.getLogger(LbOrdersStatisticsController.class);

@Autowired
public ILbOrdersStatisticsService lbOrdersStatisticsService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(LbOrdersStatistics lbOrdersStatistics) {

try {

// 构建分页类
IPage<LbOrdersStatistics> lbOrdersStatisticsPage = new Page<>(lbOrdersStatistics.getPageNo(), lbOrdersStatistics.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<LbOrdersStatistics> queryWrapper = new QueryWrapper<>(lbOrdersStatistics);
        queryWrapper.orderBy(true, lbOrdersStatistics.getIsAsc(), lbOrdersStatistics.getIsSortField());

        // 执行查询
        lbOrdersStatisticsPage = lbOrdersStatisticsService.page(lbOrdersStatisticsPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(lbOrdersStatisticsPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})LbOrdersStatistics lbOrdersStatistics) {

        try {
        lbOrdersStatisticsService.save(lbOrdersStatistics);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})LbOrdersStatistics lbOrdersStatistics) {


        try {

        lbOrdersStatisticsService.updateById(lbOrdersStatistics);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})LbOrdersStatistics lbOrdersStatistics) {

        try {

        lbOrdersStatisticsService.removeById(lbOrdersStatistics.getId());

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

            lbOrdersStatisticsService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }



}
