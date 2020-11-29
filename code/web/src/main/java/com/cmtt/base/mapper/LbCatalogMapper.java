package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbCatalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
public interface LbCatalogMapper extends BaseMapper<LbCatalog> {

    public List<LbCatalog> getLbCatalogPostList();

    public List<LbCatalog> getLbCatalogPostListByPeriodicalId(Integer periodicalId);
}
