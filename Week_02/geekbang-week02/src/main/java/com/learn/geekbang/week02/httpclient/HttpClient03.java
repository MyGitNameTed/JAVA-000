package com.learn.geekbang.week02.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class HttpClient03 {
    public static void main(String[] args) {
        String url = "http://localhost:8803/";
        getClient(url);
    }

    /**
     * get方式请求
     *
     * @param url 请求地址
     */
    private static void getClient(String url) {
        // 请求通道
        HttpClient httpClient = new DefaultHttpClient();
        // 请求配置相关参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(5000).build();
        try {
            // 请求方式
            HttpGet httpGet = new HttpGet(url);
            // 参数配置
            httpGet.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpGet);
            // 返回体
            if (200 == response.getStatusLine().getStatusCode()) {
                System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
