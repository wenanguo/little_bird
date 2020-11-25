package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.Article;
import com.cmtt.base.entity.LbPeriodical;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.R;
import com.cmtt.base.service.ILbPostService;
import com.cmtt.base.service.impl.LbPostServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
@RestController
@RequestMapping("/api/lb_post")
@Api(tags = "主页相关")
public class LbPostController {


    @Autowired
    private ILbPostService lbPostService;

    /**
     * 主页
     */
    @PostMapping("search")
    @ResponseBody
    @ApiOperation("搜索")
    public R search(@RequestBody @Valid PageInputParam params){

        // 构建分页类
        IPage<LbPost> lbPostPage = new Page<>(params.getPageNo(), params.getPageSize());

        // 构造查询及排序方式
        QueryWrapper<LbPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",params.getKeyString());
        queryWrapper.orderBy(true, params.getIsAsc(), params.getIsSortField());

        // 执行查询
        lbPostPage = lbPostService.getBaseMapper().selectPage(lbPostPage, queryWrapper);


        R r=R.ok();
        r.setPageResult(lbPostPage);
        return r;
    }
}
