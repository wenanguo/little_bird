package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.entity.LbOrders;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.SysUserOrders;
import com.cmtt.base.mapper.LbOrdersMapper;
import com.cmtt.base.service.ILbOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmtt.base.utils.RC;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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


//    /**
//     * 获取单条用户及订单列表
//     * @return
//     */
//    public SysUserOrders getOneSysUserOrders(String phone){
//        return this.baseMapper.getOneSysUserOrders(phone);
//    }


    /**
     * 获取用户及订单统计数据
     * @return
     */
    public List<Map<String,Object>> getLbOrdersStatistics(@Param(Constants.WRAPPER) Wrapper<LbOrders> queryWrapper){
        return this.baseMapper.getLbOrdersStatistics(queryWrapper);
    }


    /**
     * 判断当前用户是否包年
     * @param phone
     * @return
     */
    public boolean isPayYear(String phone){
        // 查找包年付费订单
        LbOrders lbOrders = this.getOne(Wrappers.<LbOrders>lambdaQuery()
                .eq(LbOrders::getPhone, phone)
                .eq(LbOrders::getStatus, RC.PAY_YES.code())
                .eq(LbOrders::getTtype, 2)
                .eq(LbOrders::getTradeStatus, "TRADE_SUCCESS"), false
        );

        if(lbOrders !=null){
            // 已支付包年
            return true;
        }else{
            return false;
        }

    }
}
