package com.cmtt.base.controller;

import com.cmtt.base.entity.Article;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-10-15
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/api/base/article")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public IArticleService iArticleService;


    /**
     * 登录逻辑处理
     */
    @GetMapping("/list2")
    @ResponseBody
    public R list2(Article article) throws Exception {
        throw new Exception("错误消息");
    }

    /**
     * 登录逻辑处理
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(Article article,Principal principal) {
        R httpResp = new R();


            System.out.println(principal);

       // try {

            // 构建分页类
            IPage<Article> articlePage = new Page<>(article.getPageNo(), article.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<Article> queryWrapper = new QueryWrapper<>(article);
            queryWrapper.orderBy(true, article.getIsAsc(), article.getIsSortField());

            // 执行查询
            articlePage = iArticleService.getBaseMapper().selectPage(articlePage, queryWrapper);

            // 设置返回数据
            httpResp.setPageResult(articlePage);

            //throw  new Exception("小丝儿，错误啦");

            return httpResp;

//        } catch (Exception e) {
//            e.printStackTrace();
//
//            httpResp.setCode(500);
//            httpResp.setMessage(e.getMessage());
//            return httpResp;
//        }
    }


    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody @Validated({GroupAdd.class})Article article) throws Exception {
        R httpResp = new R();



            boolean flag = iArticleService.save(article);

            //if(flag==true)throw new UnauthorizedException("未登录系统，请重新登陆");
            httpResp.setMessage("新增成功");
            httpResp.setResult(flag);

            return httpResp;

    }


    /**
     * 修改
     */
    @PutMapping("/edit")
    @ResponseBody
    public R edit(@RequestBody  @Validated({GroupEdit.class})  Article article) {
        R httpResp = new R();

        try {

            boolean flag = iArticleService.updateById(article);

            httpResp.setMessage("修改成功");
            httpResp.setResult(flag);

            return httpResp;
        } catch (Exception e) {
            e.printStackTrace();

            httpResp.setCode(500);
            httpResp.setMessage(e.getMessage());
            return httpResp;
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public R delete(@RequestBody @Validated({GroupDelete.class})Article article) {
        R httpResp = new R();

        try {

            boolean flag = iArticleService.removeById(article.getId());

            httpResp.setMessage("删除成功");
            httpResp.setResult(flag);

            return httpResp;
        } catch (Exception e) {
            e.printStackTrace();

            httpResp.setCode(500);
            httpResp.setMessage(e.getMessage());
            return httpResp;
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/batchDelete")
    @ResponseBody
    public R batchDelete(@RequestBody List<Integer> ids) {
        R httpResp = new R();

        try {

            boolean flag = iArticleService.removeByIds(ids);

            httpResp.setMessage("批量删除成功");

            httpResp.setResult(flag);

            return httpResp;
        } catch (Exception e) {
            e.printStackTrace();

            httpResp.setCode(500);
            httpResp.setMessage(e.getMessage());
            return httpResp;
        }
    }


}
