package com.cmtt.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.controller.param.GetOneGoodsInputParam;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cmtt.base.entity.LbGoods;
import com.cmtt.base.service.ILbGoodsService;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/api/lb_goods")
@Api(tags = "支付相关")
public class LbGoodsController {

    private final Logger logger = LoggerFactory.getLogger(LbGoodsController.class);

    @Autowired
    public ILbGoodsService lbGoodsService;

    /**
     * 根据商品编号获取商品
     */
    @PostMapping("/get_one")
    @ResponseBody
    @ApiOperation("获取商品列表")
    public R getOne(@RequestBody @Valid GetOneGoodsInputParam params,HttpServletRequest httpServletRequest) {

        try {
            Integer ttype=1;

            String Phonesys = httpServletRequest.getHeader("Phonesys");

            if(StringUtils.isEmpty(Phonesys)){
                return R.err().setMessage("请求头中无法获取设备类型");
            }

            if(Phonesys.equals("iOS")){
                ttype=2;
            }else if(Phonesys.equals("Android")){
                ttype=1;
            }else {
                return R.err().setMessage("请求头中无法获取设备类型");
            }



            // 执行查询
            LbGoods lbGoods = lbGoodsService.getOne(Wrappers.<LbGoods>lambdaQuery()
                    .eq(LbGoods::getTcode,params.getTcode())
                    .eq(LbGoods::getTtype,ttype)
                    .eq(LbGoods::getStatus, RC.B_NORMAL.code()));

            if(lbGoods!=null){
                // 设置返回数据
                return R.ok().setResult(lbGoods);
            }else{
                return R.err().setMessage("找不到当前商品");
            }


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }



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
    public R add(@RequestBody @Validated({GroupAdd.class}) LbGoods lbGoods) {

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
    public R edit(@RequestBody @Validated({GroupEdit.class}) LbGoods lbGoods) {


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
    public R delete(@RequestBody @Validated({GroupDelete.class}) LbGoods lbGoods) {

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
