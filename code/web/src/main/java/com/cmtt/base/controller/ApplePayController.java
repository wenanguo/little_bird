package com.cmtt.base.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.param.ApplePayValidInputParam;
import com.cmtt.base.controller.param.GetOneGoodsInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.service.ILbGoodsService;
import com.cmtt.base.service.ILbOrdersService;
import com.cmtt.base.utils.HttpUtils;
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
import java.security.Principal;
import java.time.LocalDateTime;
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

//            String notify_type=params.get("notify_type");
//            if((!StringUtils.isEmpty(notify_type))&&notify_type.equals("trade_status_sync")){
//                // 订单同步,根据订单号查询订单
//                String out_trade_no=params.get("out_trade_no");
//
//                // 创建apple pay 订单
//
//
//
//                // 查询订单 ，修改订单状态
//
//                LbOrders lbOrders = lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery().eq(LbOrders::getOutTradeNo, out_trade_no));
//
//                if(params.get("trade_status").equals("TRADE_SUCCESS")){
//                    // 支付成功 修改状态
//                    lbOrders.setStatus(RC.PAY_YES.code());
//                    lbOrders.setTradeStatus(params.get("trade_status"));
//                    lbOrders.setTradeNo(params.get("trade_no"));
//                    lbOrdersService.updateById(lbOrders);
//                }
//
//            }

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

        // 类型 1 安卓 2IOS
        Integer devType=2;
        String phone=null;
        SysUser sysUser=null;

        try {

            if(principal!=null){
                // 鉴权模式
                sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
                phone=sysUser.getPhone();
            }else{
                // 游客模式
            }



            String outtradeno=String.valueOf(System.currentTimeMillis());


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
            lbOrders.setPhone(phone); // 游客模式时，phone为空
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
    public R apple_pay_valid(@RequestBody @Valid ApplePayValidInputParam params, Principal principal, HttpServletRequest httpRequest) throws IOException {

        // 查询当前订单是否已经验证

        LbOrders lbOrders = lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery().eq(LbOrders::getOutTradeNo, params.getOut_trade_no()));

        if(lbOrders!=null){
            // 存在订单，不需要再次请求苹果验证
            // 检查是否有用户信息，如果有，而且已经有的那个钱订单，进行绑定

            if(principal!=null){
                SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();



                String phone=null;
                if(lbOrders.getPhone()==null){ // 如果订单手机号为空，则绑定

                        // 绑定订单
                        lbOrders.setPhone(sysUser.getPhone());
                        lbOrdersService.updateById(lbOrders);

                    return R.ok().setMessage("当前订单绑定成功");

                }else{
                    // 正常验证
                    lbOrders.setPhone(sysUser.getPhone());
                    return this.postAppleServer(params);
                }
            }else{
                if(lbOrders.getStatus().equals(RC.PAY_YES.code())){
                    Map<String,Object> mapRet=new HashMap<>();
                    mapRet.put("status",RC.PAY_YES.code());
                    return R.ok().setMessage("当前订单已经支付，请登录进行绑定").setResult(mapRet);
                }else{

                    return this.postAppleServer(params);
                }

            }

        }

        return R.err().setMessage("验证失败");

    }

    /**
     * 请求苹果服务器验证订单
     * @param params
     * @return
     * @throws IOException
     */
    private R postAppleServer(ApplePayValidInputParam params) throws IOException {
        // 不存在订单，请求苹果验证，并新建订单


        // 请求正式环境

        String zsurl = "https://buy.itunes.apple.com/verifyReceipt";

        Map<String, Object> map = new HashMap<>();
        map.put("receipt-data", params.getReceipt_data());
        map.put("password", "7006a41e32c24ee9b3b9af23be8b0804");
        map.put("exclude-old-transactions", false);

        System.out.println("Receipt_data" + params.getReceipt_data());
        System.out.println("Product_id" + params.getProduct_id());
        System.out.println("Transaction_id" + params.getTransaction_id());
        System.out.println("Out_trade_no" + params.getOut_trade_no());


        String req = JSON.toJSONString(map);


        HR hr = HttpUtils.doPost(zsurl, req, null);


        JSONObject jsonObject = JSONObject.parseObject(hr.getRetStr());
        if (jsonObject.get("status").equals(21007)) {


            // 请求沙盒环境

            String shurl = "https://sandbox.itunes.apple.com/verifyReceipt";

            hr = HttpUtils.doPost(shurl, req, null);


            jsonObject = JSONObject.parseObject(hr.getRetStr());

            // 支付成功 未做防盗链
            LbOrders lbOrdersNew = lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery().eq(LbOrders::getOutTradeNo, params.getOut_trade_no()));

            lbOrdersNew.setServerReq(params.toString());
            lbOrdersNew.setServerResp(hr.getRetStr());

            if (jsonObject.get("status").equals(0)) {


                if (lbOrdersNew == null) {
                    return R.err().setMessage("失败，未找到当前订单");
                }

                lbOrdersNew.setStatus(RC.PAY_YES.code());
                // 设置验证结果
                lbOrdersNew.setTradeStatus("TRADE_SUCCESS");
                lbOrdersService.updateById(lbOrdersNew);

                return R.ok().setResult(lbOrdersNew);


            }else{
                lbOrdersService.updateById(lbOrdersNew);
                Map<String,Object> mapRet=new HashMap<>();
                mapRet.put("status",jsonObject.getInteger("status"));

                return R.ok().setMessage("支付失败,状态为：" + jsonObject.get("status")).setResult(mapRet);
            }



        } else {
            return R.err().setMessage("正式环境调用错误");
        }
    }



//    /**
//     * 游客模式绑定订单
//     */
//    @PostMapping("visitor_bind")
//    @ResponseBody
//    @ApiOperation("游客模式绑定订单")
//    public R visitor_bind(@RequestBody @Valid ApplePayVisitorBindInputParam params, Principal principal, HttpServletRequest httpServletRequest){
//        SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
//
//        String phone=null;
//        if(sysUser==null){
//            return R.err().setMessage("找不到当前用户");
//        }else {
//
//            LbOrders lbOrders = lbOrdersService.getOne(Wrappers.<LbOrders>lambdaQuery().eq(LbOrders::getOutTradeNo, params.getOut_trade_no()));
//
//            if(lbOrders==null){
//                return R.err().setMessage("找不到当前订单");
//            }else{
//                lbOrders.setPhone(sysUser.getPhone());
//                lbOrdersService.updateById(lbOrders);
//                return R.ok().setMessage("订单绑定成功");
//            }
//
//        }
//
//
//
//    }


    }
