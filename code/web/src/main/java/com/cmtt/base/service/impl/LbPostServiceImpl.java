package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.mapper.LbPostMapper;
import com.cmtt.base.service.ILbPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmtt.base.utils.RC;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
@Service
public class LbPostServiceImpl extends ServiceImpl<LbPostMapper, LbPost> implements ILbPostService {


    /**
     * 获取统一的文章列表
     * @return
     */
    public LambdaQueryWrapper<LbPost> getCommonPostWrappers(){
        return this.getCommonPostWrappers(false);
    }

    /**
     * 获取统一的文章列表
     * @return
     */
    public LambdaQueryWrapper<LbPost> getCommonPostWrappers(boolean isShowPreview){

        LambdaQueryWrapper<LbPost> lbPostLambdaQueryWrapper = null;


        if(isShowPreview){
            // 展示预览
            lbPostLambdaQueryWrapper=Wrappers.<LbPost>lambdaQuery()
                    .select(LbPost.class, info -> !info.getColumn().equals("content") && !info.getColumn().equals("fee_content"))
                    .and(
                            wrapper -> wrapper
                                    .and(wrapper2 -> wrapper2
                                            .in(LbPost::getIsFree, new Integer[]{1, 2})
                                            .lt(LbPost::getPublishedAt, LocalDateTime.now()))
                                    .or()
                                    .eq(LbPost::getIsPreview, 2)

                    )
                    .eq(LbPost::getRecommend, 1)
                    .eq(LbPost::getStatus, RC.B_NORMAL.code())
                    .orderByDesc(LbPost::getPublishedAt);
        }else{
            // 不展示预览
            lbPostLambdaQueryWrapper=Wrappers.<LbPost>lambdaQuery()
                    .select(LbPost.class, info -> !info.getColumn().equals("content") && !info.getColumn().equals("fee_content"))
                    .in(LbPost::getIsFree, new Integer[]{1, 2})
                    .lt(LbPost::getPublishedAt, LocalDateTime.now())
                    .eq(LbPost::getRecommend, 1)
                    .eq(LbPost::getIsPreview, 1)
                    .eq(LbPost::getStatus, RC.B_NORMAL.code())
                    .orderByDesc(LbPost::getPublishedAt);
        }





        return lbPostLambdaQueryWrapper;
    }
}
