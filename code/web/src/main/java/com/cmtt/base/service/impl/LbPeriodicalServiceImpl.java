package com.cmtt.base.service.impl;

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



    public List<LbPeriodical> getLbPostList(){
        return this.baseMapper.getLbPostList();
    }
}
