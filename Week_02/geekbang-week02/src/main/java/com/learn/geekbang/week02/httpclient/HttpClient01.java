package com.learn.geekbang.week02.httpclient;

import org.apache.http.HttpMessage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class HttpClient01 {
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
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 返回体
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            // 请求方式
            HttpGet httpGet = new HttpGet(uri);
            // 返回体
            response = httpClient.execute(httpGet);
            if (200 == response.getStatusLine().getStatusCode()) {
                System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
