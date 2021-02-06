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
import java.util.Map;

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
            "(select phone as order_phone,out_trade_no,gmt_payment,status as order_status,trade_status,ttype as order_type,total_amount,dev_type from lb_orders where now() <DATE_ADD(gmt_payment,INTERVAL 1 YEAR) and trade_status = 'TRADE_SUCCESS'\n" +
            "and status = 203 ) as b on a.phone=b.order_phone ${ew.customSqlSegment}")
    IPage<SysUserOrders> getSysUserOrdersList(IPage<SysUserOrders> page, @Param(Constants.WRAPPER) Wrapper<SysUserOrders> queryWrapper);



    /**
     * 获取用户及订单统计数据
     * @return
     */
    @Select("select channel,dev_type,ttype,count(id) as tcount,sum(total_amount) as ttotal_amount from lb_orders ${ew.customSqlSegment} group by channel,dev_type,ttype order by channel,dev_type,ttype")
    List<Map<String,Object>> getLbOrdersStatistics(@Param(Constants.WRAPPER) Wrapper<LbOrders> queryWrapper);


//    /**
//     * 获取单条用户及订单列表
//     * @return
//     */
//    @Select("\n" +
//            "select * from sys_user as a left join \n" +
//            "(select phone as order_phone,out_trade_no,gmt_payment,status as order_status from lb_orders where now() <DATE_ADD(gmt_payment,INTERVAL 1 YEAR) and ttype=1 ) as b\n" +
//            "on a.phone=b.order_phone where phone=${phone}")
//    SysUserOrders getOneSysUserOrders(String phone);


}
