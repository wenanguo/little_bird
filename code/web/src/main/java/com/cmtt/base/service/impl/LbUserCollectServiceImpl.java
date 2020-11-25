package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.LbUserCollect;
import com.cmtt.base.mapper.LbUserCollectMapper;
import com.cmtt.base.service.ILbUserCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏文章表 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@Service
public class LbUserCollectServiceImpl extends ServiceImpl<LbUserCollectMapper, LbUserCollect> implements ILbUserCollectService {
    public IPage<LbPost> getMyCollectPostByUserId(IPage<LbPost> page, Integer id){
        return this.baseMapper.getMyCollectPostByUserId(page,id);
    }
}
