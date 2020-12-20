package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.LbPeriodical;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    public List<LbPeriodical> getLbPostList(List<Integer> ids);



}
