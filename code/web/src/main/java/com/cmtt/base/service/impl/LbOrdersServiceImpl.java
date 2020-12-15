package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.LbOrders;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.SysUserOrders;
import com.cmtt.base.mapper.LbOrdersMapper;
import com.cmtt.base.service.ILbOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品订单表 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-16
 */
@Service
public class LbOrdersServiceImpl extends ServiceImpl<LbOrdersMapper, LbOrders> implements ILbOrdersService {

    public IPage<SysUserOrders> getSysUserOrdersList(IPage<SysUserOrders> page, @Param(Constants.WRAPPER) Wrapper<SysUserOrders> queryWrapper){
        return this.baseMapper.getSysUserOrdersList(page,queryWrapper);
    }
}
