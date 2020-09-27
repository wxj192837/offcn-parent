package com.offcn.common;


import com.offcn.enums.ResponseCodeEnume;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;

public class SmsTest {
    public static void main(String[] args) throws Exception {
        String host = "http://dingxin.market.alicloudapi.com/";
        String path="dx/sendSms";
        String method = "POST";
        String appcode = "f44b9437185349ad89f5504b8e01f393";
        String tpl_id = "TP1711063";

        //请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","APPCODE " + appcode);

        //请求体
        HashMap<String, String> query = new HashMap<String, String>();
        query.put("mobile","18230202396");
        query.put("tpl_id",tpl_id);
        query.put("param","code:1234");

        //发送
        HttpResponse response = HttpUtils.doPost(host, path, method, headers, query, "");
        String s = EntityUtils.toString(response.getEntity());
        System.out.println(s);

        System.out.println(ResponseCodeEnume.FAIL.getMsg());
    }
}
