package com.cmtt.base;

import com.cmtt.base.utils.JwtUtils;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.security.Signature;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PrivateKey;

//@SpringBootTest
class DemoApplicationTests {

    CloseableHttpClient httpClient;

    @Test
    void contextLoads() {

    }


    @Test
    void test1(){



    }

    @Before
    public void setup() throws IOException {
        // 加载商户私钥（privateKey：私钥字符串）
        String privateKey="-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQD2D9o9EudQWOad\n" +
                "PsMAePhtv0EwtyNgGuO/kEEMzgrnDpsCJjxhm3NKa3GGn5K1gtfA6qXLGFsW1OnJ\n" +
                "0oS4S51VCyMDNX8wB1I9xM9OsYckKV5emLl0zl7nYFg+frJBeWlJylZvUAIgSGs1\n" +
                "kX6P1iiR4P51p1t+UE6hFVbMSsWtiyz9oh8013+RVv+wrhQaKayW1fC1o9BxFdZ1\n" +
                "pgxrCANDC3/Sr7GKrixGUjQzJBZDk3DPEh007OO0gre/2BSKkQYK/EbFVPOF3AFj\n" +
                "s+N7+M6GnEbD/LMUoRGKOWGWxKjLiaBHvDFY5r68wfd9To07o4fdG4aAAIunrccW\n" +
                "vKiQYNurAgMBAAECggEAGPi04jvX48yR4uVqrtKYC7M8L3B2k5tG/7rpDx6d9qMB\n" +
                "0i8FM7at9aB/zfTF08vqj3aAyuK249NJwqStuXJbDjVqITo9pScpDoG5tDfKCj50\n" +
                "iE8hixwif4LEkdsTxhdHOyjgqr1I6fPPhYk1ee13Ao794/zPCTunLyvWOjdgZmMl\n" +
                "hWtefmGMT0u3qOX213ntdfmRiS9oVyD6KKadq5bfoxsnRTusr0jcb3ylvyzYcbNz\n" +
                "bZDt+s8jmOybk8iv3NUX2yjQF59kf0Us9eo7mrvIS/W57pTa8CwfzJfh5CmqvaFN\n" +
                "+xe0y2IrpW1vgDen5oW0nPlIK3zEf3xi5/NV1b3O2QKBgQD7yNSfGZHbgjBJ8X+7\n" +
                "GdoLqSou9rCMTYIEF9ZrJ2uG3Dj+dtkuKvTSJCEdrvyT/A19ei0pqSdZ37zcLRqA\n" +
                "FWsNakpZ0HHxg1ysIaczngWcevnq9bXty/53cVdbJPGtUzJmVulGxZj5NXmWUcL6\n" +
                "0EsMYjRvTGpvdi1U3YcVDwvDDQKBgQD6Ln6ZLGYPzMrpdEsIdlTnfDMvYE3t8C4y\n" +
                "JR+P730bhZ9p+uvNMz8d/4I3gmZDU5Ml1JeGqqLrep7PIC7LP36MurBWewu55EGp\n" +
                "lkw2+EM3SkHeAdLHZk8UKUrHDJKhRohHNGL9jDkQBd/rU2B1RnLN+juv5upxenvt\n" +
                "ftRH1HxLlwKBgEmx2HnQ1B45lsStyMMMqANtugkQ70/Bh+KGz52BZjmMz6m7Q4n9\n" +
                "zLaNGJxFkWwHBDPLxNhCyF/H2tFe6ji/DBxbFfP/zLnxxvgiyXuULvREEk4xKNS3\n" +
                "Nn3jULw+rfYbOc82dQTf5QxcGIBk4frjaHPxcoN5DFYfctk6FcWWJ80pAoGAQ0JK\n" +
                "sL1Z9vd0ucyU+4Jskws164+1g7llgf+u+5BEDykeUWyvBiy31EXD6U56VbFbRvlf\n" +
                "f0nSoQIHPeIGEohQal8qVyWfYmt2J7SLat/VmkCxcpEE7KyZp+7t9pNvvEHtE0+H\n" +
                "KWkQUjlyGJ8CuKSm6q9SapjnAHkQZioJwgHjJKMCgYEApaQGLLgaxiTKsiL+zNZ4\n" +
                "vrDTV0PHoR5Sh8X4TPb3WRsa3XrmAEbvLZBAumKhEOVDBHKF0SuLz8Pnt0/lwdfR\n" +
                "52bMxJp7j6X5WgBWtyR4x6GDTEdzOuXTtQSDicTbJCqdVg7N3cKME9CO8DFtZ2lr\n" +
                "oUvR3+SdHMXGJre+tP6iPH4=\n" +
                "-----END PRIVATE KEY-----\n";

        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes("utf-8")));

        // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3秘钥）
        String mchId="1605717250";
        String mchSerialNo="1D5C6B5A4B2C9C849D546C6128BEDD074D3AF71C";
        String apiV3Key="AswsMz6ntu4fOHiiDeDcL7hnV4TVgcoh";
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),apiV3Key.getBytes("utf-8"));

        // 初始化httpClient
        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier)).build();
    }

//    @After
//    public void after() throws IOException {
//        httpClient.close();
//    }
//
//
//    @Test
//    public void CreateOrder() throws Exception{
//
//        this.setup();
//
//        //请求URL
//        HttpPost httpPost = new HttpPost("http://www.httpbin.org/anything");
//        // 请求body参数
//        String reqdata = "{"
//                + "\"amount\": {"
//                + "\"total\":100,"
//                + "\"currency\":\"CNY\""
//                + "},"
//                + "\"mchid\":\"1605717250\","
//                + "\"description\":\"三篇试读\","
//                + "\"notify_url\":\"https://www.weixin.qq.com/wxpay/pay.php\","
//                + "\"out_trade_no\":\"12177525012014070332333680182\","
//                + "\"goods_tag\":\"WXG\","
//                + "\"appid\":\"wx952a2a49d57ce755\","
//                + "\"attach\":\"自定义数据说明\""
////                + "\"detail\": {"
////                    + "\"invoice_id\":\"wx123\","
////                    + "\"goods_detail\": ["
////                            + "{"
////                            + "\"goods_name\":\"iPhoneX 256G\","
////                            + "\"wechatpay_goods_id\":\"1001\","
////                            + "\"quantity\":1,"
////                            + "\"merchant_goods_id\":\"123\","
////                            + "\"unit_price\":828800"
////                            + "},"
////                            + "{"
////                            + "\"goods_name\":\"iPhoneX 256G\","
////                            + "\"wechatpay_goods_id\":\"1001\","
////                            + "\"quantity\":1,"
////                            + "\"merchant_goods_id\":\"456\","
////                            + "\"unit_price\":828800"
////                            + "}"
////                    + "],"
////                    + "\"cost_price\":608800"
////                + "},"
////                + "\"scene_info\": {"
////                        + "\"store_info\": {"
////                            + "\"address\":\"广东省深圳市南山区科技中一道10000号\","
////                            + "\"area_code\":\"440305\","
////                            + "\"name\":\"腾讯大厦分店\","
////                            + "\"id\":\"0001\""
////                            + "},"
////                        + "\"device_id\":\"013467007045764\","
////                        + "\"payer_client_ip\":\"14.23.150.211\""
////                        + "}"
//                + "}";
//        StringEntity entity = new StringEntity(reqdata);
//        entity.setContentType("application/json");
//        httpPost.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");
//
//        //完成签名并执行请求
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//
//        try {
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode == 200) { //处理成功
//                System.out.println("success,return body = " + EntityUtils.toString(response.getEntity()));
//            } else if (statusCode == 204) { //处理成功，无返回Body
//                System.out.println("success");
//            } else {
//                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
//                throw new IOException("request failed");
//            }
//        } finally {
//            response.close();
//        }
//
//
//        this.after();
//    }


//
//    // Authorization: <schema> <token>
//// GET - getToken("GET", httpurl, "")
//// POST - getToken("POST", httpurl, json)
//    String schema = "WECHATPAY2-SHA256-RSA2048";
//    HttpUrl httpurl = HttpUrl.parse(url);
//
//    String getToken(String method, HttpUrl url, String body) {
//        String nonceStr = "your nonce string";
//        long timestamp = System.currentTimeMillis() / 1000;
//        String message = buildMessage(method, url, timestamp, nonceStr, body);
//        String signature = sign(message.getBytes("utf-8"));
//
//        return "mchid=\"" + yourMerchantId + "\","
//                + "nonce_str=\"" + nonceStr + "\","
//                + "timestamp=\"" + timestamp + "\","
//                + "serial_no=\"" + yourCertificateSerialNo + "\","
//                + "signature=\"" + signature + "\"";
//    }
//
//    String sign(byte[] message) {
//        Signature sign = Signature.getInstance("SHA256withRSA");
//        sign.initSign(yourPrivateKey);
//        sign.update(message);
//
//        return Base64.getEncoder().encodeToString(sign.sign());
//    }
//
//    String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
//        String canonicalUrl = url.encodedPath();
//        if (url.encodedQuery() != null) {
//            canonicalUrl += "?" + url.encodedQuery();
//        }
//
//        return method + "\n"
//                + canonicalUrl + "\n"
//                + timestamp + "\n"
//                + nonceStr + "\n"
//                + body + "\n";
//    }

}
