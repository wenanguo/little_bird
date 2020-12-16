package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.LbOrders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.SysUserOrders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商品订单表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-12-16
 */
public interface LbOrdersMapper extends BaseMapper<LbOrders> {

    /**
     * 获取用户及订单列表
     * @return
     */
    @Select("\n" +
            "select * from sys_user as a left join \n" +
            "(select phone as order_phone,out_trade_no,gmt_payment,status as order_status from lb_orders where now() <DATE_ADD(gmt_payment,INTERVAL 1 YEAR) and ttype=1 ) as b\n" +
            "on a.phone=b.order_phone ${ew.customSqlSegment}")
    IPage<SysUserOrders> getSysUserOrdersList(IPage<SysUserOrders> page, @Param(Constants.WRAPPER) Wrapper<SysUserOrders> queryWrapper);


    /**
     * 获取单条用户及订单列表
     * @return
     */
    @Select("\n" +
            "select * from sys_user as a left join \n" +
            "(select phone as order_phone,out_trade_no,gmt_payment,status as order_status from lb_orders where now() <DATE_ADD(gmt_payment,INTERVAL 1 YEAR) and ttype=1 ) as b\n" +
            "on a.phone=b.order_phone where phone=${phone}")
    SysUserOrders getOneSysUserOrders(String phone);


}
