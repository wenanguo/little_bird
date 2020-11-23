package com.cmtt.base.service.impl;

import com.cmtt.base.entity.LbPost;
import com.cmtt.base.mapper.LbPostMapper;
import com.cmtt.base.service.ILbPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
