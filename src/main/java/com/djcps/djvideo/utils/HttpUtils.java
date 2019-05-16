package com.djcps.djvideo.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *HttpClient封装类
 * @author 有缘
 */
public class HttpUtils {

    private static Gson gson = new Gson();

    /**
     * 封装http get方法
     * @param url
     * @return
     */
    public static Map<String, Object> doGet(String url) {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        Map<String, Object> map = new HashMap<String, Object>();

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setRedirectsEnabled(true)
                .setSocketTimeout(5000)
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String httopResult = EntityUtils.toString(httpResponse.getEntity());
                map = gson.fromJson(httopResult, map.getClass());
            }
        } catch (Exception e) {

        }
        return map;
    }

    /**
     * 封装post方法
     * @param url
     * @param data
     * @param timeout
     * @return
     */
    public String doPost(String url, String data, int timeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setRedirectsEnabled(true)
                .setSocketTimeout(timeout)
                .build();

        HttpPost httpPost = new HttpPost();
        httpPost.setConfig(requestConfig);

        httpPost.setHeader("Content-Type","text/html;charset=UTF-8");

        if(data != null && data instanceof String){
            StringEntity stringEntity = new StringEntity(data,"UTF-8");
          httpPost.setEntity(stringEntity);
        }
        try{
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String httpResult = EntityUtils.toString(httpEntity);
                return httpResult;
            }
        }catch (Exception e){

        }finally {
            try{}catch (Exception e){

            }
        }
        return null;
    }

}
