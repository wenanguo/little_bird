package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.controller.param.UserCollectInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbUserCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 用户收藏文章表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/lb_user_collect")
@Api(tags = "主页相关")
public class LbUserCollectController {

    private final Logger logger = LoggerFactory.getLogger(LbUserCollectController.class);

    @Autowired
    private ILbUserCollectService lbUserCollectService;



    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("收藏文章")
    public R add(@RequestBody @Valid UserCollectInputParam params,@ApiIgnore Principal principal) throws Exception {

        try{
            LbUserCollect lbUserCollect=new LbUserCollect();
            BeanUtils.copyProperties(params,lbUserCollect);
            System.out.println(principal);

            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
            lbUserCollect.setUserId(sysUser.getId());
            lbUserCollect.setPostId(params.getPostId());

            lbUserCollectService.save(lbUserCollect);

            return R.ok().setMessage("收藏成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.err().setMessage("收藏失败");
        }

    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @ResponseBody
    @ApiOperation("删除收藏文章")
    public R delete(@RequestBody @Valid UserCollectInputParam params,@ApiIgnore Principal principal) throws Exception {

        try{

            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();


            lbUserCollectService.remove(Wrappers.<LbUserCollect>lambdaQuery().eq(LbUserCollect::getUserId, sysUser.getId())
                    .eq(LbUserCollect::getPostId,params.getPostId()));

            return R.ok().setMessage("取消收藏成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.err().setMessage("取消收藏失败");
        }

    }


    /**
     * 获取我收藏的文章
     */
    @PostMapping("/get_mycollect")
    @ResponseBody
    @ApiOperation("获取我收藏的文章")
    public R getMyCollect(@RequestBody @Valid PageInputParam params, @ApiIgnore Principal principal) throws Exception {

        try{

            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            // 构建分页类
            IPage<LbPost> lbPostPage = new Page<>(params.getPageNo(), params.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbPost> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderBy(true, params.getIsAsc(), params.getIsSortField());


            lbPostPage = lbUserCollectService.getMyCollectPostByUserId(lbPostPage,sysUser.getId());



            R r=R.ok().setMessage("获取成功");
            r.setPageResult(lbPostPage);
            return r;
            //lbUserCollectService.list(Wrappers.<LbUserCollect>lambdaQuery().eq(LbUserCollect::getUserId, sysUser.getId()));


        }catch (Exception e){
            e.printStackTrace();
            return R.err().setMessage("获取失败");
        }

    }




    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(LbUserCollect lbUserCollect) {

        try {

// 构建分页类
            IPage<LbUserCollect> lbUserCollectPage = new Page<>(lbUserCollect.getPageNo(), lbUserCollect.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<LbUserCollect> queryWrapper = new QueryWrapper<>(lbUserCollect);
            queryWrapper.orderBy(true, lbUserCollect.getIsAsc(), lbUserCollect.getIsSortField());

            // 执行查询
            lbUserCollectPage = lbUserCollectService.getBaseMapper().selectPage(lbUserCollectPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(lbUserCollectPage);


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
//    public R add(@RequestBody @Validated({GroupAdd.class})LbUserCollect lbUserCollect) {
//
//        try {
//            lbUserCollectService.save(lbUserCollect);
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
    public R edit(@RequestBody  @Validated({GroupEdit.class})LbUserCollect lbUserCollect) {


        try {

            lbUserCollectService.updateById(lbUserCollect);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) GetOneInputParam lbUserCollect) {

        try {

            lbUserCollectService.removeById(lbUserCollect.getId());

            return R.ok().setMessage("删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("删除失败");
        }
    }




}
