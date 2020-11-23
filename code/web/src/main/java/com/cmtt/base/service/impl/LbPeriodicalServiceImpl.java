package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.LbPeriodical;
import com.cmtt.base.mapper.LbPeriodicalMapper;
import com.cmtt.base.service.ILbPeriodicalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 期刊表 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-20
 */
@Service
public class LbPeriodicalServiceImpl extends ServiceImpl<LbPeriodicalMapper, LbPeriodical> implements ILbPeriodicalService {



    public IPage<LbPeriodical> getLbPostList(IPage<LbPeriodical> page){

        return this.baseMapper.getLbPostList(page);
    }
}
