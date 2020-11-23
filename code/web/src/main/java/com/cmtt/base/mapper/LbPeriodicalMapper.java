package com.cmtt.base.mapper;

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

    public List<LbPeriodical> getLbPostList();
}
