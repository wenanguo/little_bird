package com.cmtt.base.controllerv2;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.param.GetOneGoodsInputParam;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.service.*;
import com.cmtt.base.service.impl.WxPayServiceImpl;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 栏目表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/wx/")
@Api(tags = "V2-微信支付")
public class WxPayController {

    private final Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Autowired
    private ILbGoodsService lbGoodsService;

    @Autowired
    private ILbOrdersService lbOrdersService;

    @Autowired
    private WxPayServiceImpl wxPayService;


    /**
     * 主页
     */
    @PostMapping("notify_url")
    @ResponseBody
    @ApiOperation("微信异步通知接口")
    public R notify_url(HttpServletRequest request){

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
        System.out.println("微信支付异步通知："+params);

        return R.ok();
    }

    /**
     * 主页
     */
    @PostMapping("create_trade")
    @ResponseBody
    @ApiOperation("统一创建订单")
    public R create_trade(@RequestBody @Valid GetOneGoodsInputParam params, Principal principal, HttpServletRequest httpServletRequest){
        SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
        if(sysUser==null){
            return R.err().setMessage("找不到用户信息");
        }

        String outtradeno=String.valueOf(System.currentTimeMillis());

        // 类型 1 安卓 2IOS
        Integer devType=1;

        String returnStr="";

        // 判断设备类型
        String Phonesys = httpServletRequest.getHeader("Phonesys");

        if(org.apache.commons.lang.StringUtils.isEmpty(Phonesys)){
            return R.err().setMessage("请求头中无法获取设备类型");
        }

        if(Phonesys.equals("iOS")){
            devType=2;
        }else if(Phonesys.equals("Android")){
            devType=1;
        }else {
            return R.err().setMessage("请求头中无法获取设备类型");
        }


        // 根据商品编码获取商品信息
        // 执行查询
        LbGoods lbGoods = lbGoodsService.getOne(Wrappers.<LbGoods>lambdaQuery()
                .eq(LbGoods::getTcode,params.getTcode())
                .eq(LbGoods::getDevType,devType)
                .eq(LbGoods::getStatus, RC.B_NORMAL.code()),false);

        if(lbGoods==null){
            return R.err().setMessage("找不到当前商品");

        }

        String notify_url="http://www.teamyy.cn:18087/api/wx/notify_url";



//        //实例化客户端
//        //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
//
//        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setBody(lbGoods.getBody());
//        model.setSubject(lbGoods.getTitle());
//        model.setOutTradeNo(outtradeno);
//        model.setTimeoutExpress("30m");
//        model.setTotalAmount(String.valueOf(lbGoods.getPrice()));
//        model.setProductCode("QUICK_MSECURITY_PAY");
//        request.setBizModel(model);
//        request.setNotifyUrl(aliPayService.getNotifyUrl());


        try {

//            //这里和普通的接口调用不同，使用的是sdkExecute
//            AlipayTradeAppPayResponse response = aliPayService.getAlipayClient().sdkExecute(request);
//            returnStr=response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
            returnStr=wxPayService.WxCreateOrder(lbGoods.getPrice().intValue(),lbGoods.getTitle(),notify_url,outtradeno);

            Map returnMap = (Map)JSON.parse(returnStr);

            // 入库商户订单
            LbOrders lbOrders= new LbOrders();
            lbOrders.setGoodsId(lbGoods.getId());
            lbOrders.setDevType(devType);
            lbOrders.setTtype(lbGoods.getTtype());
            lbOrders.setPhone(sysUser.getPhone());
            lbOrders.setTradeNo(returnMap.get("prepay_id").toString());
            lbOrders.setOutTradeNo(outtradeno);
            lbOrders.setTotalAmount(lbGoods.getPrice());
            lbOrders.setBuyerPayAmount(lbGoods.getPrice());
            lbOrders.setGmtCreate(LocalDateTime.now());
            lbOrders.setGmtPayment(LocalDateTime.now());
            lbOrders.setStatus(RC.PAY_NO.code());

//            lbOrders.setServerReq(JSON.toJSONString(request));
            lbOrders.setServerResp(returnStr);


            lbOrdersService.save(lbOrders);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return R.ok().setResult((Map)JSON.parse(returnStr));
    }



}
