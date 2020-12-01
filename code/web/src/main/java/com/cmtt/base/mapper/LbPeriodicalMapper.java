package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.LbPeriodical;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    public List<LbPeriodical> getLbPostList(@Param("ids") List<Integer> ids);
}
