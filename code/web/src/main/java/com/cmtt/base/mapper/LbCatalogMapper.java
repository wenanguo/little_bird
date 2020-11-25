package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbCatalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
public interface LbCatalogMapper extends BaseMapper<LbCatalog> {

    public IPage<LbCatalog> getLbCatalogPostList(IPage<LbCatalog> page);
}
