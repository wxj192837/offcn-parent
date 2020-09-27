package com.offcn.common;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        //使用httpclient创建一个对象
        HttpClient httpClient = new DefaultHttpClient();
        //构造一个请求
        HttpGet httpGet = new HttpGet("http://www.offcn.com");

        //通过客户端发送请求
        HttpResponse response = httpClient.execute(httpGet);
        //获取response中的数据
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity,"gbk");
        System.out.println(s);
    }
}
