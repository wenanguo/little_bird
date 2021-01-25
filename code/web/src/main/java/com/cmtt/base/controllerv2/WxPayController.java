package com.cmtt.base.controllerv2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.param.GetOneGoodsInputParam;
import com.cmtt.base.controller.param.GetOneInputParam;
import com.cmtt.base.controller.param.WxQueryTradeInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.service.*;
import com.cmtt.base.service.impl.WxPayServiceImpl;
import com.cmtt.base.utils.RC;
import com.cmtt.base.utils.WxAPIV3AesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private ILbPayOrderService lbPayOrderService;


    /**
     * 主页
     */
    @PostMapping("notify_url")
    @ResponseBody
    @ApiOperation("微信异步通知接口")
    public Map notify_url(HttpServletRequest request) throws IOException, GeneralSecurityException {


        String body=ReadAsChars(request);
        JSONObject parse = (JSONObject) JSON.parse(body);
        String event_type=parse.getString("event_type");

        if((!StringUtils.isEmpty(event_type))&&event_type.equals("TRANSACTION.SUCCESS")) {


            String nonce = parse.getJSONObject("resource").getString("nonce");
            String associated_data = parse.getJSONObject("resource").getString("associated_data");
            String ciphertext = parse.getJSONObject("resource").getString("ciphertext");


            String apiV3Key = "AswsMz6ntu4fOHiiDeDcL7hnV4TVgcoh";

            //解密回调信息
            byte[] key = apiV3Key.getBytes("UTF-8");
            WxAPIV3AesUtil aesUtil = new WxAPIV3AesUtil(key);
            String decryptToString = aesUtil.decryptToString(associated_data.getBytes("UTF-8"), nonce.getBytes("UTF-8"), ciphertext);


            // 解码后的json 对象
            JSONObject decryptToJson = (JSONObject) JSON.parse(decryptToString);

            // 订单同步,根据订单号查询订单
            String out_trade_no=decryptToJson.getString("out_trade_no");


            // 查询订单 ，修改订单状态

            LbOrders lbOrders = lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery().eq(LbOrders::getOutTradeNo, out_trade_no));

            if(decryptToJson.getString("trade_state").equals("SUCCESS")){
                // 支付成功 修改状态
                lbOrders.setStatus(RC.PAY_YES.code());
                lbOrders.setTradeStatus("TRADE_SUCCESS");
                lbOrders.setTradeNo(decryptToJson.getString("transaction_id"));
                lbOrdersService.updateById(lbOrders);
            }


        }

        Map map=new HashMap();
        map.put("code","SUCCESS");
        return map;
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

        Map returnMap=null;

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
        //String notify_url="http://wenanguo.5gzvip.idcfengye.com/api/wx/notify_url";



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
            returnMap=wxPayService.WxCreateOrder(lbGoods.getPrice(),lbGoods.getTitle(),notify_url,outtradeno);

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
            lbOrders.setServerResp(returnMap.toString());


            lbOrdersService.save(lbOrders);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return R.ok().setResult(returnMap);
    }



    /**
     * 主页
     */
    @PostMapping("query_trade")
    @ResponseBody
    @ApiOperation("微信查询订单状态")
    public R query_trade(@RequestBody @Valid WxQueryTradeInputParam param) throws Exception {
        Map map=wxPayService.WxQueryOrder(param.getOutTradeNo());

        return R.ok().setResult(map);
    }


    // 字符串读取
    // 方法一
    public static String ReadAsChars(HttpServletRequest request)
    {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
