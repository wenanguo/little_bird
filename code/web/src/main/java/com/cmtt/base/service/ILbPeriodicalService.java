package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.LbPeriodical;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 期刊表 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
public interface ILbPeriodicalService extends IService<LbPeriodical> {
    public IPage<LbPeriodical> getLbPostList(IPage<LbPeriodical> page);
}
