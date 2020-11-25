package com.cmtt.base.controller;


import com.cmtt.base.controller.param.ReportInputParam;
import com.cmtt.base.entity.Article;
import com.cmtt.base.entity.LbReport;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.service.ILbReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @Autowired
    private ILbReportService iLbReportService;

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

            iLbReportService.save(lbReport);

            return R.ok().setMessage("提交成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.err().setMessage("提交失败");
        }

    }

}
