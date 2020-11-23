package com.cmtt.base.service;

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
    public List<LbPeriodical> getLbPostList();
}
