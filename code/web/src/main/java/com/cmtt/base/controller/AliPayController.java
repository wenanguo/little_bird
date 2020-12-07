package com.cmtt.base.controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.cmtt.base.controller.param.AlipayTradeAppPayInputParam;
import com.cmtt.base.entity.LbPayOrder;
import com.cmtt.base.entity.R;
import com.cmtt.base.service.ILbPayOrderService;
import com.cmtt.base.service.impl.AliPayServiceImpl;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
@Api(tags = "支付相关")
public class AliPayController {


    private final Logger logger = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private AliPayServiceImpl aliPayService;

    @Autowired
    private ILbPayOrderService lbPayOrderService;


    /**
     * 回调接口
     */
    @PostMapping("callback")
    @ResponseBody
    public R zfbCallback(HttpServletRequest request) throws AlipayApiException {


        try {

            int contentLength = request.getContentLength();
            if (contentLength < 0) {
                return null;
            }
            byte buffer[] = new byte[contentLength];
            for (int i = 0; i < contentLength; ) {

                int readlen = request.getInputStream().read(buffer, i, contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
            }

            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            String jsonbody = new String(buffer, charEncoding);
            System.out.println("==========支付宝callback======");
            System.out.println("callback：" + jsonbody);
            System.out.println("=============================");

        } catch (Exception e) {
            e.printStackTrace();
        }


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
        boolean flag = AlipaySignature.rsaCheckV1(params, aliPayService.getAlipayPublicKey(), aliPayService.getCharset(), aliPayService.getSignType());

        System.out.println(flag);
        System.out.println(params);

        return R.ok();

    }

    /**
     * 创建订单接口
     */
    @PostMapping("alipay_trade_app_pay")
    @ResponseBody
    @ApiOperation("创建支付宝订单")
    public R alipay_trade_app_pay(@RequestBody @Valid AlipayTradeAppPayInputParam params, HttpServletRequest httpRequest){

        String outtradeno=String.valueOf(System.currentTimeMillis());
        System.out.println(outtradeno);

        String returnStr="";

        //实例化客户端
        //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(params.getBody());
        model.setSubject(params.getSubject());
        model.setOutTradeNo(outtradeno);
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(aliPayService.getNotifyUrl());
        try {

            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = aliPayService.getAlipayClient().sdkExecute(request);
            returnStr=response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。



            // 入库
            LbPayOrder lbPayOrder=new LbPayOrder();
            lbPayOrder.setOutTradeNo(outtradeno);
            lbPayOrder.setTradeNo(response.getTradeNo());
            lbPayOrder.setBody(params.getBody());
            lbPayOrder.setSubject(params.getSubject());
            lbPayOrder.setTotalAmount(Float.valueOf(params.getTotal_amount()));
            lbPayOrder.setResponse(JSON.toJSONString(response).toString());

            lbPayOrder.setStatus(RC.PAY_NO.code());

            lbPayOrderService.save(lbPayOrder);


            System.out.println(returnStr);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return R.ok().setResult(returnStr);
    }



    /**
     * 创建订单接口
     */
    @PostMapping("alipay_trade_query")
    @ResponseBody
    @ApiOperation("创建支付宝订单")
    public R alipay_trade_query(@RequestBody @Valid AlipayTradeAppPayInputParam params, HttpServletRequest httpRequest){

        String outtradeno=String.valueOf(System.currentTimeMillis());
        System.out.println(outtradeno);

        String returnStr="";

        //实例化客户端
        //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(params.getBody());
        model.setSubject(params.getSubject());
        model.setOutTradeNo(outtradeno);
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(aliPayService.getNotifyUrl());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeQueryResponse response = aliPayService.getAlipayClient().sdkExecute(request);
            returnStr=response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
            System.out.println(returnStr);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return R.ok().setResult(returnStr);
    }




}
