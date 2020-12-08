package com.cmtt.base;
//import com.alipay.easysdk.factory.Factory;
//import com.alipay.easysdk.factory.Factory.Payment;
//import com.alipay.easysdk.kernel.Config;
//import com.alipay.easysdk.kernel.util.ResponseChecker;
//import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;



public class Zfb {

//
//        public static void main(String[] args) throws Exception {
//            // 1. 设置参数（全局只需设置一次）
//            Factory.setOptions(getOptions());
//            try {
//                // 2. 发起API调用（以创建当面付收款二维码为例）
//                AlipayTradePrecreateResponse response = Payment.FaceToFace()
//                        .preCreate("Apple iPhone11 128G", "2234567890", "5799.00");
//                // 3. 处理响应或异常
//                if (ResponseChecker.success(response)) {
//                    System.out.println("调用成功");
//                } else {
//                    System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
//                }
//            } catch (Exception e) {
//                System.err.println("调用遭遇异常，原因：" + e.getMessage());
//                throw new RuntimeException(e.getMessage(), e);
//            }
//        }
//
//        private static Config getOptions() {
//            Config config = new Config();
//            config.protocol = "https";
//            config.gatewayHost = "openapi.alipay.com";
//            config.signType = "RSA2";
//
//            config.appId = "2021002112681191";
//
//            // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
//            config.merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBpcUnwtYFwWK0H17ksotm8buq9DmOEQaXP7Frjfivl/NdOro/yj33ZDptehAwG8VDBNWkm/C4sMyqGbd0LJEUVXfCE9vfi0q1P99JpaC0XXUw3Tu7kPlC5FIOlCngVbGzm88JQZ88ZrU7FAidXCY1ct+bk8G2WVQkFDwaODiPKNeD0tmZGb3UgT/6NE92KE5Q8fqArI+8WGJtdHt4YbdBsxLO020Q2JNxkDToSmYUeqQiZRu4UEZFoHpmpAa6etiYbU2RCwp0peDYIElyv4YVIxqOHN8RFXHLaUBboT3w8lXc/Pyq0jE5Z0IgEdHOu0PNm4DPWaPHCoq4qBkUMo1tAgMBAAECggEAARyczjR7hV0dLZI/TBLD40QbaeHiRHec6Nsg+F0lTswsyBXi1v0y/tYbMikjzmXOkT6PXnP/4nWRq7vAuPxQ2i3Jcx6cehRFtbwwe/8xm5pZkOu0wW0C1jIDCiD3IvdZKMMjSV8IsIno7/LCCCdsJHUxubHQrXGiqRSLV2Y4BfI+cKHfPfURJhlJDCwpeQTHqEJawoC/qmBeBVDwTguR7x0nWK7LVArfkFIiTZRLf8ZVwmeeFKYecxQhpVqO6acjxtpF19tb54sCTLHTJJDHfqoGwMEu2BZRcAbOyti8A8u0fJH2r2G6c92UMGaznrBOQ+AsAxCiPoUZI5Ci/ew5AQKBgQD4AsA+spmZk3YyY/zm73M0/aoba2QTq8laljQ1vTNXdDErPV3pVDp5zHyOXWIC5pb4ZiiuOHNx2DvEOGiEEH0JP141UWC38gHEpFaPoPkO847mC3ZJO5fP40/V99Yq0lAtRPTCuS7q5CbFRwKPqD9jOCu9Iyo5+PcjaBJiTCV3jQKBgQCF0uxmI2Yg/W3VWYmQEuQqKV4u3QXnDc+IcMdHGuWASdLG2HSZOG0VdO4DmTY+tMwyOydAvM+TMtKd0TbVeMWV9NKmT4Qq7NDSizC/G5q1VbnSrJZel+wVc2AaYF1bwr8u2IKk1TUdhYWzhNbDy2oiGG0VGiA9jMfxfVPds3SFYQKBgEtqwr0OIIlD/0hZgXlUTUtK6+SEpp0YuxUx4dSJoUgxzBXjBmDCvTl2hlXgbcKGDlig8DPydKCk6Z/Eq3ula/BY9bPu+CNXk1QRyUTJ5ZK3W7f2ulSQq5yK+icNw3GoHLt3YZqpJ2xETsnkyxGWTxhD54EPZXLVk4mPxy9fCnGtAoGAXZuGhINqYtUiyMPSt5afRuac3Mb54/8DFftdSV2E/1GkQDcUiECx/PpHqvi2uvIYi2JZOK1ZvSslrAK2CmhqPVR9LJweZTA1ebCnLmaOtxz52GWk4RH/r5l+tbSGWTNGK+yCpe4FM1s8hK/80gQtfx/63XHi72rUSRVTXpaygMECgYEA1gknXAew+7XudAtZSSy85+m6DZjAWNG+op5Tw5VHf5mFmfLX+W+vEgUVZYwORrTXmAnPRB9xCJ0ITRIMLjM/xG5aN9Y+S6NbMW95TTdBkhT2FDSIa7mGNjTehqCZmrUZ9bspLX1iqfgjNJrcAjwpBHbwkfqYUa5t8qCFXA8gkq8=";
//
//            //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
//            //config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
//            //config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
//            //config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";
//
//            //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
//            config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaXFJ8LWBcFitB9e5LKLZvG7qvQ5jhEGlz+xa434r5fzXTq6P8o992Q6bXoQMBvFQwTVpJvwuLDMqhm3dCyRFFV3whPb34tKtT/fSaWgtF11MN07u5D5QuRSDpQp4FWxs5vPCUGfPGa1OxQInVwmNXLfm5PBtllUJBQ8Gjg4jyjXg9LZmRm91IE/+jRPdihOUPH6gKyPvFhibXR7eGG3QbMSztNtENiTcZA06EpmFHqkImUbuFBGRaB6ZqQGunrYmG1NkQsKdKXg2CBJcr+GFSMajhzfERVxy2lAW6E98PJV3Pz8qtIxOWdCIBHRzrtDzZuAz1mjxwqKuKgZFDKNbQIDAQAB";
//
//            //可设置异步通知接收服务地址（可选）
//            config.notifyUrl = "http://www.teamyy.cn:18087/api/zfb/callback -->";
//
//            //可设置AES密钥，调用AES加解密相关接口时需要（可选）
//            config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";
//
//            return config;
//        }

}
