package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.LbOrders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.SysUserOrders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品订单表 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-16
 */
public interface ILbOrdersService extends IService<LbOrders> {
    /**
     * 获取用户及订单列表
     * @return
     */
    IPage<SysUserOrders> getSysUserOrdersList(IPage<SysUserOrders> page, @Param(Constants.WRAPPER) Wrapper<SysUserOrders> queryWrapper);


    /**
     * 获取单条用户及订单列表
     * @return
     */
    SysUserOrders getOneSysUserOrders(String phone);


    /**
     * 判断当前用户是否包年
     * @param phone
     * @return
     */
    boolean isPayYear(String phone);
}
