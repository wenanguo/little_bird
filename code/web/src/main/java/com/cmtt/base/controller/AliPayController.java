package com.cmtt.base.controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.param.AlipayTradeAppPayInputParam;
import com.cmtt.base.controller.param.AlipayTradeAppQueryInputParam;
import com.cmtt.base.controller.param.GetOneGoodsInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.service.ILbGoodsService;
import com.cmtt.base.service.ILbOrdersService;
import com.cmtt.base.service.ILbPayOrderService;
import com.cmtt.base.service.impl.AliPayServiceImpl;
import com.cmtt.base.service.impl.LbGoodsServiceImpl;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 支付宝 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/zfb")
@Api(tags = "支付宝相关")
public class AliPayController {


    private final Logger logger = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private AliPayServiceImpl aliPayService;

    @Autowired
    private ILbPayOrderService lbPayOrderService;

    @Autowired
    private ILbGoodsService lbGoodsService;

    @Autowired
    private ILbOrdersService lbOrdersService;

    /**
     * 回调接口
     */
    @PostMapping("callback")
    @ResponseBody
    public R zfbCallback(HttpServletRequest request) throws AlipayApiException {


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
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //String alipaypublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaXFJ8LWBcFitB9e5LKLZvG7qvQ5jhEGlz+xa434r5fzXTq6P8o992Q6bXoQMBvFQwTVpJvwuLDMqhm3dCyRFFV3whPb34tKtT/fSaWgtF11MN07u5D5QuRSDpQp4FWxs5vPCUGfPGa1OxQInVwmNXLfm5PBtllUJBQ8Gjg4jyjXg9LZmRm91IE/+jRPdihOUPH6gKyPvFhibXR7eGG3QbMSztNtENiTcZA06EpmFHqkImUbuFBGRaB6ZqQGunrYmG1NkQsKdKXg2CBJcr+GFSMajhzfERVxy2lAW6E98PJV3Pz8qtIxOWdCIBHRzrtDzZuAz1mjxwqKuKgZFDKNbQIDAQAB";
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayService.getAlipayPublicKey(), aliPayService.getCharset(), aliPayService.getSignType());


         //signVerified =true;

        if (signVerified){
            // TODO 验签成功后
            //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure

            String notify_type=params.get("notify_type");
            if((!StringUtils.isEmpty(notify_type))&&notify_type.equals("trade_status_sync")){
                // 订单同步,根据订单号查询订单
                String out_trade_no=params.get("out_trade_no");
                LbPayOrder lbPayOrder = lbPayOrderService.getOne(Wrappers.<LbPayOrder>lambdaQuery().eq(LbPayOrder::getOutTradeNo, out_trade_no),false);

                lbPayOrder.setTradeNo(params.get("trade_no"));


                lbPayOrder.setSellerId(params.get("seller_id"));
                lbPayOrder.setSellerEmail(params.get("seller_email"));
                lbPayOrder.setBuyerId(params.get("buyer_id"));
                lbPayOrder.setBuyerLogonId(params.get("buyer_logon_id"));
                lbPayOrder.setTradeStatus(params.get("trade_status"));
                lbPayOrder.setTotalAmount(new BigDecimal(params.get("total_amount")));
                lbPayOrder.setReceiptAmount(new BigDecimal(params.get("receipt_amount")));
                lbPayOrder.setInvoiceAmount(new BigDecimal(params.get("invoice_amount")));
                lbPayOrder.setBuyerPayAmount(new BigDecimal(params.get("buyer_pay_amount")));
                lbPayOrder.setPointAmount(new BigDecimal(params.get("point_amount")));
                //lbPayOrder.setRefundFee(new BigDecimal(params.get("refund_fee")));

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                lbPayOrder.setGmtCreate(LocalDateTime.parse(params.get("gmt_create"), dtf));
                lbPayOrder.setGmtPayment(LocalDateTime.parse(params.get("gmt_payment"), dtf));
                //lbPayOrder.setGmtRefund(LocalDateTime.parse(params.get("gmt_refund"), dtf));
                //lbPayOrder.setGmtClose(LocalDateTime.parse(params.get("gmt_close"), dtf));
                lbPayOrder.setFundBillList(params.get("fund_bill_list"));
                lbPayOrder.setPassbackParams(params.get("passback_params"));
                lbPayOrder.setVoucherDetailList(params.get("voucher_detail_list"));

                lbPayOrder.setNotifyResponse(params.toString());
                lbPayOrderService.updateById(lbPayOrder);


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


        System.out.println("==========支付宝notify======");
        System.out.println(signVerified);
        System.out.println(params);
        System.out.println("=============================");


        return R.ok();

    }

    /**
     * 创建订单接口
     */
    @PostMapping("alipay_trade_app_pay")
    @ResponseBody
    @ApiOperation("创建支付宝订单")
    public R alipay_trade_app_pay(@RequestBody @Valid GetOneGoodsInputParam params, Principal principal, HttpServletRequest httpServletRequest){

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



//
//
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

            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = aliPayService.alipayTradeAppPay(lbGoods.getBody(),lbGoods.getTitle(),String.valueOf(lbGoods.getPrice()),outtradeno);
            returnStr=response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。


            // 入库支付宝订单
            LbPayOrder lbPayOrder=new LbPayOrder();
            lbPayOrder.setOutTradeNo(outtradeno);
            lbPayOrder.setTradeNo(response.getTradeNo());
            lbPayOrder.setBody(lbGoods.getBody());
            lbPayOrder.setSubject(lbGoods.getTitle());
            lbPayOrder.setTotalAmount(lbGoods.getPrice());
            lbPayOrder.setTradeAppPayResponse(JSON.toJSONString(response));

            lbPayOrder.setStatus(RC.PAY_NO.code());

            lbPayOrderService.save(lbPayOrder);


            // 入库商户订单
            LbOrders lbOrders= new LbOrders();
            lbOrders.setChannel("aliPay"); // 支付渠道
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

            //lbOrders.setServerReq(JSON.toJSONString(request));
            lbOrders.setServerResp(JSON.toJSONString(response));


            lbOrdersService.save(lbOrders);


        } catch (Exception e) {
            e.printStackTrace();
        }




        return R.ok().setResult(returnStr);
    }



    /**
     * 创建订单接口
     */
    @PostMapping("alipay_trade_query")
    @ResponseBody
    @ApiOperation("查询支付宝订单")
    public R alipay_trade_query(@RequestBody @Valid AlipayTradeAppQueryInputParam params){
        Map map =new HashMap();


        try {

            AlipayTradeQueryResponse response = aliPayService.alipayTradeQuery(params.getOutTradeNo(), params.getTradeNo());
            if(response.isSuccess()){

                map.put("code",response.getCode());
                map.put("msg",response.getMsg());
                map.put("tradeStatus",response.getTradeStatus());
                map.put("totalAmount",response.getTotalAmount());
                map.put("tradeNo",response.getTradeNo());
                map.put("outTradeNo",response.getOutTradeNo());

                return R.ok().setResult(map);
            } else {

                map.put("code",response.getCode());

                return R.err().setResult(map);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return R.err().setMessage("订单查询请求失败");

    }


}
