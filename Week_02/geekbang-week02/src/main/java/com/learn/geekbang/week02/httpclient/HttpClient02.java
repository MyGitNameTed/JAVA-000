package com.learn.geekbang.week02.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClient02 {
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
        RestTemplate restTemplate = new RestTemplate();
        String message = restTemplate.getForObject(url, String.class);
        System.out.println(message);
    }
}
