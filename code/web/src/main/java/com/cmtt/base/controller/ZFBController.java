package com.cmtt.base.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.controller.param.AlipayTradeAppPayInputParam;
import com.cmtt.base.controller.param.PageInputParam;
import com.cmtt.base.entity.LbAd;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ILbAdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
public class ZFBController {


    private String APP_ID="2021002112681191";
    private String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBpcUnwtYFwWK0H17ksotm8buq9DmOEQaXP7Frjfivl/NdOro/yj33ZDptehAwG8VDBNWkm/C4sMyqGbd0LJEUVXfCE9vfi0q1P99JpaC0XXUw3Tu7kPlC5FIOlCngVbGzm88JQZ88ZrU7FAidXCY1ct+bk8G2WVQkFDwaODiPKNeD0tmZGb3UgT/6NE92KE5Q8fqArI+8WGJtdHt4YbdBsxLO020Q2JNxkDToSmYUeqQiZRu4UEZFoHpmpAa6etiYbU2RCwp0peDYIElyv4YVIxqOHN8RFXHLaUBboT3w8lXc/Pyq0jE5Z0IgEdHOu0PNm4DPWaPHCoq4qBkUMo1tAgMBAAECggEAARyczjR7hV0dLZI/TBLD40QbaeHiRHec6Nsg+F0lTswsyBXi1v0y/tYbMikjzmXOkT6PXnP/4nWRq7vAuPxQ2i3Jcx6cehRFtbwwe/8xm5pZkOu0wW0C1jIDCiD3IvdZKMMjSV8IsIno7/LCCCdsJHUxubHQrXGiqRSLV2Y4BfI+cKHfPfURJhlJDCwpeQTHqEJawoC/qmBeBVDwTguR7x0nWK7LVArfkFIiTZRLf8ZVwmeeFKYecxQhpVqO6acjxtpF19tb54sCTLHTJJDHfqoGwMEu2BZRcAbOyti8A8u0fJH2r2G6c92UMGaznrBOQ+AsAxCiPoUZI5Ci/ew5AQKBgQD4AsA+spmZk3YyY/zm73M0/aoba2QTq8laljQ1vTNXdDErPV3pVDp5zHyOXWIC5pb4ZiiuOHNx2DvEOGiEEH0JP141UWC38gHEpFaPoPkO847mC3ZJO5fP40/V99Yq0lAtRPTCuS7q5CbFRwKPqD9jOCu9Iyo5+PcjaBJiTCV3jQKBgQCF0uxmI2Yg/W3VWYmQEuQqKV4u3QXnDc+IcMdHGuWASdLG2HSZOG0VdO4DmTY+tMwyOydAvM+TMtKd0TbVeMWV9NKmT4Qq7NDSizC/G5q1VbnSrJZel+wVc2AaYF1bwr8u2IKk1TUdhYWzhNbDy2oiGG0VGiA9jMfxfVPds3SFYQKBgEtqwr0OIIlD/0hZgXlUTUtK6+SEpp0YuxUx4dSJoUgxzBXjBmDCvTl2hlXgbcKGDlig8DPydKCk6Z/Eq3ula/BY9bPu+CNXk1QRyUTJ5ZK3W7f2ulSQq5yK+icNw3GoHLt3YZqpJ2xETsnkyxGWTxhD54EPZXLVk4mPxy9fCnGtAoGAXZuGhINqYtUiyMPSt5afRuac3Mb54/8DFftdSV2E/1GkQDcUiECx/PpHqvi2uvIYi2JZOK1ZvSslrAK2CmhqPVR9LJweZTA1ebCnLmaOtxz52GWk4RH/r5l+tbSGWTNGK+yCpe4FM1s8hK/80gQtfx/63XHi72rUSRVTXpaygMECgYEA1gknXAew+7XudAtZSSy85+m6DZjAWNG+op5Tw5VHf5mFmfLX+W+vEgUVZYwORrTXmAnPRB9xCJ0ITRIMLjM/xG5aN9Y+S6NbMW95TTdBkhT2FDSIa7mGNjTehqCZmrUZ9bspLX1iqfgjNJrcAjwpBHbwkfqYUa5t8qCFXA8gkq8=";
    private String CHARSET="utf-8";
    private String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaXFJ8LWBcFitB9e5LKLZvG7qvQ5jhEGlz+xa434r5fzXTq6P8o992Q6bXoQMBvFQwTVpJvwuLDMqhm3dCyRFFV3whPb34tKtT/fSaWgtF11MN07u5D5QuRSDpQp4FWxs5vPCUGfPGa1OxQInVwmNXLfm5PBtllUJBQ8Gjg4jyjXg9LZmRm91IE/+jRPdihOUPH6gKyPvFhibXR7eGG3QbMSztNtENiTcZA06EpmFHqkImUbuFBGRaB6ZqQGunrYmG1NkQsKdKXg2CBJcr+GFSMajhzfERVxy2lAW6E98PJV3Pz8qtIxOWdCIBHRzrtDzZuAz1mjxwqKuKgZFDKNbQIDAQAB";


    private final Logger logger = LoggerFactory.getLogger(ZFBController.class);

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
        boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2");

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
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
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
        request.setNotifyUrl("http://www.teamyy.cn:18087/api/zfb/callback");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            returnStr=response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
            System.out.println(returnStr);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return R.ok().setResult(returnStr);
    }

}
