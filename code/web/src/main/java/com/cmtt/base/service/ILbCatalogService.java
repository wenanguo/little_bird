package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbCatalog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmtt.base.entity.LbSubject;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
public interface ILbCatalogService extends IService<LbCatalog> {

    public List<LbCatalog> getLbCatalogPostList();

    public List<LbCatalog> getLbCatalogPostListByPeriodicalId(Integer periodicalId);

}
