package com.cmtt.base.service.impl;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.cmtt.base.controller.LbAdController;
import io.lettuce.core.output.ScanOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBpcUnwtYFwWK0H17ksotm8buq9DmOEQaXP7Frjfivl/NdOro/yj33ZDptehAwG8VDBNWkm/C4sMyqGbd0LJEUVXfCE9vfi0q1P99JpaC0XXUw3Tu7kPlC5FIOlCngVbGzm88JQZ88ZrU7FAidXCY1ct+bk8G2WVQkFDwaODiPKNeD0tmZGb3UgT/6NE92KE5Q8fqArI+8WGJtdHt4YbdBsxLO020Q2JNxkDToSmYUeqQiZRu4UEZFoHpmpAa6etiYbU2RCwp0peDYIElyv4YVIxqOHN8RFXHLaUBboT3w8lXc/Pyq0jE5Z0IgEdHOu0PNm4DPWaPHCoq4qBkUMo1tAgMBAAECggEAARyczjR7hV0dLZI/TBLD40QbaeHiRHec6Nsg+F0lTswsyBXi1v0y/tYbMikjzmXOkT6PXnP/4nWRq7vAuPxQ2i3Jcx6cehRFtbwwe/8xm5pZkOu0wW0C1jIDCiD3IvdZKMMjSV8IsIno7/LCCCdsJHUxubHQrXGiqRSLV2Y4BfI+cKHfPfURJhlJDCwpeQTHqEJawoC/qmBeBVDwTguR7x0nWK7LVArfkFIiTZRLf8ZVwmeeFKYecxQhpVqO6acjxtpF19tb54sCTLHTJJDHfqoGwMEu2BZRcAbOyti8A8u0fJH2r2G6c92UMGaznrBOQ+AsAxCiPoUZI5Ci/ew5AQKBgQD4AsA+spmZk3YyY/zm73M0/aoba2QTq8laljQ1vTNXdDErPV3pVDp5zHyOXWIC5pb4ZiiuOHNx2DvEOGiEEH0JP141UWC38gHEpFaPoPkO847mC3ZJO5fP40/V99Yq0lAtRPTCuS7q5CbFRwKPqD9jOCu9Iyo5+PcjaBJiTCV3jQKBgQCF0uxmI2Yg/W3VWYmQEuQqKV4u3QXnDc+IcMdHGuWASdLG2HSZOG0VdO4DmTY+tMwyOydAvM+TMtKd0TbVeMWV9NKmT4Qq7NDSizC/G5q1VbnSrJZel+wVc2AaYF1bwr8u2IKk1TUdhYWzhNbDy2oiGG0VGiA9jMfxfVPds3SFYQKBgEtqwr0OIIlD/0hZgXlUTUtK6+SEpp0YuxUx4dSJoUgxzBXjBmDCvTl2hlXgbcKGDlig8DPydKCk6Z/Eq3ula/BY9bPu+CNXk1QRyUTJ5ZK3W7f2ulSQq5yK+icNw3GoHLt3YZqpJ2xETsnkyxGWTxhD54EPZXLVk4mPxy9fCnGtAoGAXZuGhINqYtUiyMPSt5afRuac3Mb54/8DFftdSV2E/1GkQDcUiECx/PpHqvi2uvIYi2JZOK1ZvSslrAK2CmhqPVR9LJweZTA1ebCnLmaOtxz52GWk4RH/r5l+tbSGWTNGK+yCpe4FM1s8hK/80gQtfx/63XHi72rUSRVTXpaygMECgYEA1gknXAew+7XudAtZSSy85+m6DZjAWNG+op5Tw5VHf5mFmfLX+W+vEgUVZYwORrTXmAnPRB9xCJ0ITRIMLjM/xG5aN9Y+S6NbMW95TTdBkhT2FDSIa7mGNjTehqCZmrUZ9bspLX1iqfgjNJrcAjwpBHbwkfqYUa5t8qCFXA8gkq8=";
    private String CHARSET="utf-8";
    private String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaXFJ8LWBcFitB9e5LKLZvG7qvQ5jhEGlz+xa434r5fzXTq6P8o992Q6bXoQMBvFQwTVpJvwuLDMqhm3dCyRFFV3whPb34tKtT/fSaWgtF11MN07u5D5QuRSDpQp4FWxs5vPCUGfPGa1OxQInVwmNXLfm5PBtllUJBQ8Gjg4jyjXg9LZmRm91IE/+jRPdihOUPH6gKyPvFhibXR7eGG3QbMSztNtENiTcZA06EpmFHqkImUbuFBGRaB6ZqQGunrYmG1NkQsKdKXg2CBJcr+GFSMajhzfERVxy2lAW6E98PJV3Pz8qtIxOWdCIBHRzrtDzZuAz1mjxwqKuKgZFDKNbQIDAQAB";
    private String NOTIFYURL="http://www.teamyy.cn:18087/api/zfb/callback";

    private String SING_TYPE="RSA2";
    // 阿里支付客户端
    private AlipayClient alipayClient;


    AliPayServiceImpl(){

        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", this.APP_ID, this.APP_PRIVATE_KEY, "json", this.CHARSET, this.ALIPAY_PUBLIC_KEY, this.SING_TYPE);
        logger.info("阿里支付服务初始化完成");
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
