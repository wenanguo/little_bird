package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.LbUserCollect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户收藏文章表 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
public interface ILbUserCollectService extends IService<LbUserCollect> {
    public IPage<LbPost> getMyCollectPostByUserId(IPage<LbPost> page, Integer id);
}
