package com.cmtt.base.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

    /**
     * 打印http内容，打印完成后request清空
     * @param request
     * @return
     */
    public String printRequest(HttpServletRequest request){

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


            return jsonbody;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
