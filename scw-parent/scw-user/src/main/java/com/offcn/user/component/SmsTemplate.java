package com.offcn.user.component;

import com.offcn.common.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SmsTemplate {
    @Value("${sms.host}")
    private String host ;
    @Value("${sms.path}")
    private String path;
    @Value("${sms.method}")
    private String method ;
    @Value("${sms.appcode}")
    private String appcode ;
    @Value("${sms.tpl_id}")
    private String tpl_id ;

    /**
     * @Author dsy
     * @Date 2020/9/23 16:25
     * @Description This is description of method
     * @Param [query]  "mobile" "param"
     * @Return java.lang.String
     * @Since version-1.0
     */

    public String sendSms(Map<String,String> query){
        // 请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","APPCODE " + appcode);
        // 请求体
        query.put("tpl_id",tpl_id);
        HttpResponse response = null;
        // 发送
        try {
            if(method.equalsIgnoreCase("get")){ // get
                response = HttpUtils.doGet(host, path, method, headers, query);
            }else{ // post
                response = HttpUtils.doPost(host, path, method, headers, query, "");
            }
            String result = EntityUtils.toString(response.getEntity());

            return  result;
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }


}
