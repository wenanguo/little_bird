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

import com.cmtt.base.entity.LbProtocol;
import com.cmtt.base.service.ILbProtocolService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 协议信息表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-01
 */
@RestController
@RequestMapping("/api/base/lb-protocol")
public class LbProtocolController {

private final Logger logger = LoggerFactory.getLogger(LbProtocolController.class);

@Autowired
public ILbProtocolService lbProtocolService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(LbProtocol lbProtocol) {

try {

// 构建分页类
IPage<LbProtocol> lbProtocolPage = new Page<>(lbProtocol.getPageNo(), lbProtocol.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<LbProtocol> queryWrapper = new QueryWrapper<>(lbProtocol);
        queryWrapper.orderBy(true, lbProtocol.getIsAsc(), lbProtocol.getIsSortField());

        // 执行查询
        lbProtocolPage = lbProtocolService.getBaseMapper().selectPage(lbProtocolPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(lbProtocolPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})LbProtocol lbProtocol) {

        try {
        lbProtocolService.save(lbProtocol);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})LbProtocol lbProtocol) {


        try {

        lbProtocolService.updateById(lbProtocol);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})LbProtocol lbProtocol) {

        try {

        lbProtocolService.removeById(lbProtocol.getId());

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

            lbProtocolService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }



}
