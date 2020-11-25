package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbCatalog;
import com.cmtt.base.mapper.LbCatalogMapper;
import com.cmtt.base.service.ILbCatalogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@Service
public class LbCatalogServiceImpl extends ServiceImpl<LbCatalogMapper, LbCatalog> implements ILbCatalogService {


    public IPage<LbCatalog> getLbCatalogPostList(IPage<LbCatalog> page){
        return this.baseMapper.getLbCatalogPostList(page);
    }
}
