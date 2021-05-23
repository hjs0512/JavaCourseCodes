package org.geekbang.u.course.homework.c02nio;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by hjs on 2021/5/17.
 */
public class HttpClientHelper {

    public static CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * GET 调用
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getAsString(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = null;
        try {
            response1 = httpclient.execute(httpGet);
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            return EntityUtils.toString(entity1, "UTF-8");
        } finally {
            if (null != response1) {
                response1.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8801";
        String text = HttpClientHelper.getAsString(url);
        System.out.println("url: " + url + " ; response: \n" + text);
    }
}
