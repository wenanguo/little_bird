package com.cmtt.base.service.impl;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 阿里支付 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@Service
public class AliPayServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);


    private String APP_ID="2021002112681191";
    // 应用私钥
    private String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBpcUnwtYFwWK0H17ksotm8buq9DmOEQaXP7Frjfivl/NdOro/yj33ZDptehAwG8VDBNWkm/C4sMyqGbd0LJEUVXfCE9vfi0q1P99JpaC0XXUw3Tu7kPlC5FIOlCngVbGzm88JQZ88ZrU7FAidXCY1ct+bk8G2WVQkFDwaODiPKNeD0tmZGb3UgT/6NE92KE5Q8fqArI+8WGJtdHt4YbdBsxLO020Q2JNxkDToSmYUeqQiZRu4UEZFoHpmpAa6etiYbU2RCwp0peDYIElyv4YVIxqOHN8RFXHLaUBboT3w8lXc/Pyq0jE5Z0IgEdHOu0PNm4DPWaPHCoq4qBkUMo1tAgMBAAECggEAARyczjR7hV0dLZI/TBLD40QbaeHiRHec6Nsg+F0lTswsyBXi1v0y/tYbMikjzmXOkT6PXnP/4nWRq7vAuPxQ2i3Jcx6cehRFtbwwe/8xm5pZkOu0wW0C1jIDCiD3IvdZKMMjSV8IsIno7/LCCCdsJHUxubHQrXGiqRSLV2Y4BfI+cKHfPfURJhlJDCwpeQTHqEJawoC/qmBeBVDwTguR7x0nWK7LVArfkFIiTZRLf8ZVwmeeFKYecxQhpVqO6acjxtpF19tb54sCTLHTJJDHfqoGwMEu2BZRcAbOyti8A8u0fJH2r2G6c92UMGaznrBOQ+AsAxCiPoUZI5Ci/ew5AQKBgQD4AsA+spmZk3YyY/zm73M0/aoba2QTq8laljQ1vTNXdDErPV3pVDp5zHyOXWIC5pb4ZiiuOHNx2DvEOGiEEH0JP141UWC38gHEpFaPoPkO847mC3ZJO5fP40/V99Yq0lAtRPTCuS7q5CbFRwKPqD9jOCu9Iyo5+PcjaBJiTCV3jQKBgQCF0uxmI2Yg/W3VWYmQEuQqKV4u3QXnDc+IcMdHGuWASdLG2HSZOG0VdO4DmTY+tMwyOydAvM+TMtKd0TbVeMWV9NKmT4Qq7NDSizC/G5q1VbnSrJZel+wVc2AaYF1bwr8u2IKk1TUdhYWzhNbDy2oiGG0VGiA9jMfxfVPds3SFYQKBgEtqwr0OIIlD/0hZgXlUTUtK6+SEpp0YuxUx4dSJoUgxzBXjBmDCvTl2hlXgbcKGDlig8DPydKCk6Z/Eq3ula/BY9bPu+CNXk1QRyUTJ5ZK3W7f2ulSQq5yK+icNw3GoHLt3YZqpJ2xETsnkyxGWTxhD54EPZXLVk4mPxy9fCnGtAoGAXZuGhINqYtUiyMPSt5afRuac3Mb54/8DFftdSV2E/1GkQDcUiECx/PpHqvi2uvIYi2JZOK1ZvSslrAK2CmhqPVR9LJweZTA1ebCnLmaOtxz52GWk4RH/r5l+tbSGWTNGK+yCpe4FM1s8hK/80gQtfx/63XHi72rUSRVTXpaygMECgYEA1gknXAew+7XudAtZSSy85+m6DZjAWNG+op5Tw5VHf5mFmfLX+W+vEgUVZYwORrTXmAnPRB9xCJ0ITRIMLjM/xG5aN9Y+S6NbMW95TTdBkhT2FDSIa7mGNjTehqCZmrUZ9bspLX1iqfgjNJrcAjwpBHbwkfqYUa5t8qCFXA8gkq8=";
    private String CHARSET="utf-8";
    // 应用公钥
    // private String APP_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaXFJ8LWBcFitB9e5LKLZvG7qvQ5jhEGlz+xa434r5fzXTq6P8o992Q6bXoQMBvFQwTVpJvwuLDMqhm3dCyRFFV3whPb34tKtT/fSaWgtF11MN07u5D5QuRSDpQp4FWxs5vPCUGfPGa1OxQInVwmNXLfm5PBtllUJBQ8Gjg4jyjXg9LZmRm91IE/+jRPdihOUPH6gKyPvFhibXR7eGG3QbMSztNtENiTcZA06EpmFHqkImUbuFBGRaB6ZqQGunrYmG1NkQsKdKXg2CBJcr+GFSMajhzfERVxy2lAW6E98PJV3Pz8qtIxOWdCIBHRzrtDzZuAz1mjxwqKuKgZFDKNbQIDAQAB";

    // 支付宝公钥
    private String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj9cFIxlM0p5Gxldf0/mhCE9fnm+71SxA0SvCvWBx6IzRb+UTILmgYi4yQBKqtTLQX99vkJpGgPJPIdvtTsFCeoiR4EbZ2+a2ZT+/DQDYbrLIWale3UaQwssqti9c68iNV20r4BkFxwqG4KPxFLiXylFpbUsbhjqBiLWmIj7p2E8xPQlKxXKyl/VNG+K9L2MOnXunAce1IFL+zDj9kWZEpahSofxJBMvCVjC62g1C47PUM8Z3x33FHUDOnE2WHv2Rf6nqDJQcvVRUKupQRHyqCOdCBE/YAUKxYDN20Tm90axitIPklpfX/Y6vDQG/66J1kXoH2sy/5r81XgXA/abGrQIDAQAB";

    @Value("${spring.pay.ali_pay.notify:https://app.aves.art/api/zfb/callback}")
    private String NOTIFYURL;

    private String SING_TYPE="RSA2";
    // 阿里支付客户端
    private AlipayClient alipayClient;


    AliPayServiceImpl(){

        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", this.APP_ID, this.APP_PRIVATE_KEY, "json", this.CHARSET, this.ALIPAY_PUBLIC_KEY, this.SING_TYPE);
        logger.info("阿里支付服务初始化完成");
    }


    /**
     * 订单状态查询
     * @param out_trade_no
     * @param trade_no
     * @return
     */
    public AlipayTradeQueryResponse alipayTradeQuery(String out_trade_no,String trade_no){

        //实例化客户端

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(out_trade_no);
        model.setTradeNo(trade_no);


        request.setBizModel(model);

        try {

            AlipayTradeQueryResponse response = this.getAlipayClient().execute(request);

            return response;
        }catch (Exception e){
            e.printStackTrace();
        }


            return null;

    }


    /**
     * 统一创建订单
     * @param body
     * @param subject
     * @param totalAmount
     * @param out_trade_no
     * @return
     */
    public AlipayTradeAppPayResponse alipayTradeAppPay(String body,String subject,String totalAmount,String out_trade_no){

        //实例化客户端
        //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(out_trade_no);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(String.valueOf(totalAmount));
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(this.getNotifyUrl());
        try {

            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = this.getAlipayClient().sdkExecute(request);

            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
            return null;

    }
    /**
     * 获取公钥
     * @return
     */
    public String getAlipayPublicKey(){
        return this.ALIPAY_PUBLIC_KEY;
    }

    /**
     * 获取编码
     * @return
     */
    public String getCharset(){
        return this.CHARSET;
    }


    /**
     * 获取加密算法
     * @return
     */
    public String getSignType(){
        return this.SING_TYPE;
    }


    /**
     * 获取回调地址
     * @return
     */
    public String getNotifyUrl(){
        return this.NOTIFYURL;
    }

    /**
     * 返回单例阿里客户端
     * @return
     */
    public AlipayClient getAlipayClient(){
       return this.alipayClient;
    }
}
