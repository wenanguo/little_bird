package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.entity.LbPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmtt.base.utils.RC;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
public interface ILbPostService extends IService<LbPost> {

    /**
     * 获取统一的文章列表
     * @return
     */
    public LambdaQueryWrapper<LbPost> getCommonPostWrappers();
}
