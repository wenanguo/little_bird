package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmtt.base.entity.LbPeriodical;
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

    List<LbPeriodical> getLbPostList(@Param("ids") List<Integer> ids);

}
