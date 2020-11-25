package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbPeriodical;
import com.cmtt.base.entity.LbSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 栏目表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
public interface LbSubjectMapper extends BaseMapper<LbSubject> {
    public IPage<LbSubject> getLbSubjectPostList(IPage<LbSubject> page);
}
