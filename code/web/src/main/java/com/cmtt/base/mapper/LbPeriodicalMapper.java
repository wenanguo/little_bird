package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.LbPeriodical;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 期刊表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
public interface LbPeriodicalMapper extends BaseMapper<LbPeriodical> {

    public IPage<LbPeriodical> getLbPostList(IPage<LbPeriodical> page);
}
