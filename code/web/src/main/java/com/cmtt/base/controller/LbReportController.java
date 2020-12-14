package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.ReportInputParam;
import com.cmtt.base.entity.Article;
import com.cmtt.base.entity.LbReport;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 举报表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/lb_report")
@Api(tags = "主页相关")
public class LbReportController {

    private final Logger logger = LoggerFactory.getLogger(LbReportController.class);

    @Autowired
    private ILbReportService lbReportService;

    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("举报")
    public R add(@RequestBody @Valid ReportInputParam params) throws Exception {

        try{
            LbReport lbReport=new LbReport();
            BeanUtils.copyProperties(params,lbReport);



            lbReportService.save(lbReport);

            return R.ok().setMessage("提交成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.err().setMessage("提交失败");
        }

    }



    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbReport lbReport) {

        try {

// 构建分页类
            IPage<LbReport> lbReportPage = new Page<>(lbReport.getPageNo(), lbReport.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbReport> queryWrapper = new QueryWrapper<>(lbReport);
            queryWrapper.orderBy(true, lbReport.getIsAsc(), lbReport.getIsSortField());

            // 执行查询
            lbReportPage = lbReportService.getBaseMapper().selectPage(lbReportPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbReportPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }


//    /**
//     * 新增
//     */
//    @PostMapping("/add")
//    @ResponseBody
//    public R add(@RequestBody @Validated({GroupAdd.class})LbReport lbReport) {
//
//        try {
//            lbReportService.save(lbReport);
//
//            return R.ok().setMessage("新增成功");
//
//        } catch (Exception e) {
//            logger.warn(e.getMessage());
//
//            return R.err().setMessage("新增失败");
//        }
//
//
//    }


    /**
     * 修改
     */
    @PutMapping("/edit")
    @ResponseBody
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbReport lbReport) {


        try {

            lbReportService.updateById(lbReport);

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
    public R delete(@RequestBody @Validated({GroupDelete.class})LbReport lbReport) {

        try {

            lbReportService.removeById(lbReport.getId());

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

            lbReportService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }




}
