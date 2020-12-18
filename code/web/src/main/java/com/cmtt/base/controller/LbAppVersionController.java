package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.AppUpdateInputParam;
import com.cmtt.base.controller.param.GetAuthorPostInputParam;
import com.cmtt.base.entity.LbAppVersion;
import com.cmtt.base.entity.LbAuthor;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbAppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/lb_app_version")
@Api(tags = "主页相关")
public class LbAppVersionController {

    @Autowired
    private ILbAppVersionService lbAppVersionService;

    private final Logger logger = LoggerFactory.getLogger(LbAppVersionController.class);

    /**
     * 获取作者及所属文章
     */
    @PostMapping("app_update")
    @ResponseBody
    @ApiOperation("app更新")
    public R appUpdate(@RequestBody @Valid AppUpdateInputParam params){



        LbAppVersion lbAppVersion = lbAppVersionService.getOne(Wrappers.<LbAppVersion>lambdaQuery().gt(LbAppVersion::getInnerVersion,params.getCurVersion()).orderByDesc(LbAppVersion::getId),false);

        if(lbAppVersion!=null ) {

            // 判断当前版本是否小于最新版本，如果是，返回最新版本

            return R.ok().setResult(lbAppVersion);
        }else{
            return R.ok().setMessage("当前已经为最新版");
        }




    }



    // 后台


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbAppVersion lbAppVersion) {

        try {

// 构建分页类
            IPage<LbAppVersion> lbAppVersionPage = new Page<>(lbAppVersion.getPageNo(), lbAppVersion.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbAppVersion> queryWrapper = new QueryWrapper<>(lbAppVersion);
            queryWrapper.orderBy(true, lbAppVersion.getIsAsc(), lbAppVersion.getIsSortField());

            // 执行查询
            lbAppVersionPage = lbAppVersionService.getBaseMapper().selectPage(lbAppVersionPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbAppVersionPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class})LbAppVersion lbAppVersion) {

        try {
            lbAppVersionService.save(lbAppVersion);

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
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbAppVersion lbAppVersion) {


        try {

            lbAppVersionService.updateById(lbAppVersion);

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
    public R delete(@RequestBody @Validated({GroupDelete.class})LbAppVersion lbAppVersion) {

        try {

            lbAppVersionService.removeById(lbAppVersion.getId());

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

            lbAppVersionService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }




}
