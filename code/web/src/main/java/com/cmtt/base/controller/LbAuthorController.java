package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.GetAuthorPostInputParam;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbAuthorService;
import com.cmtt.base.service.ILbPostService;
import com.cmtt.base.utils.RC;
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

    private final Logger logger = LoggerFactory.getLogger(LbAuthorController.class);

    @Autowired
    private ILbAuthorService lbAuthorService;

    @Autowired
    private ILbPostService lbPostService;

    /**
     * 获取作者及所属文章
     */
    @PostMapping("get_author_post")
    @ResponseBody
    @ApiOperation("获取作者及所属文章（文章）")
    public R getAuthorPost(@RequestBody @Valid GetAuthorPostInputParam params){

        LbAuthor lbAuthor = lbAuthorService.getOne(Wrappers.<LbAuthor>lambdaQuery().eq(LbAuthor::getId, params.getId()),false);

        if(lbAuthor!=null){

            // 获取统一的文章查询条件
            LambdaQueryWrapper<LbPost> queryWrapper = lbPostService.getCommonPostWrappers()
                    .like(LbPost::getAuthor, lbAuthor.getName());

            List<LbPost> list = lbPostService.list(queryWrapper);
            lbAuthor.setLbPostList(list);
            return R.ok().setResult(lbAuthor);
        }else{

            return R.err().setMessage("找不当当前期刊数据");
        }


    }

    /**
     * 获取所有列表
     */
    @GetMapping("/list_all")
    @ResponseBody
    public R listAll() {

        try {

            // 执行查询
            List<LbAuthor> list = lbAuthorService.list(Wrappers.<LbAuthor>lambdaQuery().eq(LbAuthor::getStatus, RC.B_NORMAL.code()));

            // 设置返回数据
            return R.ok().setResult(list);


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
    public R list(LbAuthor lbAuthor) {

        try {

// 构建分页类
            IPage<LbAuthor> lbAuthorPage = new Page<>(lbAuthor.getPageNo(), lbAuthor.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbAuthor> queryWrapper = new QueryWrapper<>(lbAuthor);
            queryWrapper.orderBy(true, lbAuthor.getIsAsc(), lbAuthor.getIsSortField());

            // 执行查询
            lbAuthorPage = lbAuthorService.getBaseMapper().selectPage(lbAuthorPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbAuthorPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class})LbAuthor lbAuthor) {

        try {
            lbAuthorService.save(lbAuthor);

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
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbAuthor lbAuthor) {


        try {

            lbAuthorService.updateById(lbAuthor);








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
    public R delete(@RequestBody @Validated({GroupDelete.class})GetOneInputParam lbAuthor) {

        try {

            lbAuthorService.removeById(lbAuthor.getId());

            return R.ok().setMessage("删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("删除失败");
        }
    }




}
