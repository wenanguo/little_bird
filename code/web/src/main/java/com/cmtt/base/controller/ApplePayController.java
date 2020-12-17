package com.cmtt.base.controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.param.AlipayTradeAppQueryInputParam;
import com.cmtt.base.controller.param.ApplePayValidInputParam;
import com.cmtt.base.controller.param.GetOneGoodsInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.service.ILbGoodsService;
import com.cmtt.base.service.ILbOrdersService;
import com.cmtt.base.service.ILbPayOrderService;
import com.cmtt.base.service.impl.AliPayServiceImpl;
import com.cmtt.base.utils.HttpclientUtils;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * apple_apy 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/apple_pay")
@Api(tags = "ApplePay相关")
public class ApplePayController {


    private final Logger logger = LoggerFactory.getLogger(ApplePayController.class);


    @Autowired
    private ILbGoodsService lbGoodsService;

    @Autowired
    private ILbOrdersService lbOrdersService;


    /**
     * 回调接口
     */
    @PostMapping("callback")
    @ResponseBody
    public R applePayCallback(HttpServletRequest request) throws AlipayApiException {


        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }



         boolean signVerified =true;

        if (signVerified){
            // TODO 验签成功后
            //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure

            String notify_type=params.get("notify_type");
            if((!StringUtils.isEmpty(notify_type))&&notify_type.equals("trade_status_sync")){
                // 订单同步,根据订单号查询订单
                String out_trade_no=params.get("out_trade_no");

                // 创建apple pay 订单



                // 查询订单 ，修改订单状态

                LbOrders lbOrders = lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery().eq(LbOrders::getOutTradeNo, out_trade_no));

                if(params.get("trade_status").equals("TRADE_SUCCESS")){
                    // 支付成功 修改状态
                    lbOrders.setStatus(RC.PAY_YES.code());
                    lbOrders.setTradeStatus(params.get("trade_status"));
                    lbOrders.setTradeNo(params.get("trade_no"));
                    lbOrdersService.updateById(lbOrders);
                }

            }

        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
        }


        System.out.println("==========apple apy notify======");
        System.out.println(signVerified);
        System.out.println(params);
        System.out.println("=============================");


        return R.ok();

    }

    /**
     * 创建订单接口
     */
    @PostMapping("apple_pay_create")
    @ResponseBody
    @ApiOperation("创建apple pay 订单")
    public R apple_pay_create(@RequestBody @Valid GetOneGoodsInputParam params, Principal principal, HttpServletRequest httpServletRequest){



        try {

            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
            if(sysUser==null){
                return R.err().setMessage("找不到用户信息");
            }

            String outtradeno=String.valueOf(System.currentTimeMillis());

            // 类型 1 安卓 2IOS
            Integer devType=2;

            String returnStr="";

            // 判断设备类型是否是苹果
            String Phonesys = httpServletRequest.getHeader("Phonesys");

            if(!Phonesys.equals("iOS")){
                return R.err().setMessage("apple pay 只支持苹果客户端");
            }


            // 根据商品编码获取商品信息
            // 执行查询
            LbGoods lbGoods = lbGoodsService.getOne(Wrappers.<LbGoods>lambdaQuery()
                    .eq(LbGoods::getTcode,params.getTcode())
                    .eq(LbGoods::getDevType,devType)
                    .eq(LbGoods::getStatus, RC.B_NORMAL.code()));

            if(lbGoods==null){
                return R.err().setMessage("找不到当前商品");

            }


            // 入库 apple_pay 订单



            // 入库商户订单
            LbOrders lbOrders = new LbOrders();
            lbOrders.setGoodsId(lbGoods.getId());
            lbOrders.setDevType(devType);
            lbOrders.setTtype(lbGoods.getTtype());
            lbOrders.setPhone(sysUser.getPhone());
            lbOrders.setTradeNo("");
            lbOrders.setOutTradeNo(outtradeno);
            lbOrders.setTotalAmount(lbGoods.getPrice());
            lbOrders.setBuyerPayAmount(lbGoods.getPrice());
            lbOrders.setGmtCreate(LocalDateTime.now());
            lbOrders.setGmtPayment(LocalDateTime.now());
            lbOrders.setStatus(RC.PAY_NO.code());

            lbOrdersService.save(lbOrders);

            return R.ok().setResult(lbOrders);

        } catch (Exception e) {
            e.printStackTrace();
        }




        return R.err().setMessage("创建订单失败");
    }



    /**
     * 验证订单支付情况接口
     */
    @PostMapping("apple_pay_valid")
    @ResponseBody
    @ApiOperation("验证订单支付情况接口")
    public R apple_pay_valid(@RequestBody @Valid ApplePayValidInputParam params, HttpServletRequest httpRequest) throws IOException {

        // 请求正式环境

        String zsurl="https://buy.itunes.apple.com/verifyReceipt";

        Map<String,Object> map=new HashMap<>();
        map.put("receipt-data",params.getReceipt_data());
        map.put("password","");
        map.put("exclude-old-transactions",false);

        System.out.println(params.getPhone());
        System.out.println(params.getOut_trade_no());


        String a=JSON.toJSONString(map);


        HR hr = HttpclientUtils.doPost(zsurl,a,null);


        System.out.println(hr);



        // 请求沙盒环境

        String shurl="https://buy.itunes.apple.com/verifyReceipt";

        map=new HashMap<>();
        map.put("receipt-data",params.getReceipt_data());
        map.put("password","aaa");
        map.put("exclude-old-transactions",false);

        System.out.println(params.getPhone());
        System.out.println(params.getOut_trade_no());


        System.out.println(JSON.toJSONString(map));


        hr = HttpclientUtils.doPost(shurl,a,null);


        System.out.println(hr);




        LbOrders lbOrders = lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery().eq(LbOrders::getOutTradeNo, params.getOut_trade_no()));


        // 设置验证结果

        lbOrders.setTradeStatus("apple_pay_succee");

        return R.ok().setResult(lbOrders);

    }




}
