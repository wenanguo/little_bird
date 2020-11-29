package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.controller.param.GetAuthorPostInputParam;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.entity.LbAuthor;
import com.cmtt.base.entity.LbPeriodical;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.R;
import com.cmtt.base.service.ILbAuthorService;
import com.cmtt.base.service.ILbPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 作者表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/lb_author")
@Api(tags = "主页相关")
public class LbAuthorController {

    @Autowired
    private ILbAuthorService lbAuthorService;

    @Autowired
    private ILbPostService lbPostService;

    /**
     * 获取作者及所属文章
     */
    @PostMapping("get_author_post")
    @ResponseBody
    @ApiOperation("获取作者及所属文章")
    public R getAuthorPost(@RequestBody @Valid GetAuthorPostInputParam params){

        LbAuthor lbAuthor = lbAuthorService.getOne(Wrappers.<LbAuthor>lambdaQuery().eq(LbAuthor::getId, params.getId()));

        if(lbAuthor!=null){
            List<LbPost> list = lbPostService.list(Wrappers.<LbPost>lambdaQuery().like(LbPost::getAuthor, lbAuthor.getName()));
            lbAuthor.setLbPostList(list);
            return R.ok().setResult(lbAuthor);
        }else{

            return R.err().setMessage("找不当当前期刊数据");
        }


    }

}
