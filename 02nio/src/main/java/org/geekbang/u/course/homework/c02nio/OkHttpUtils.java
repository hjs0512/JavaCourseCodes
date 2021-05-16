package org.geekbang.u.course.homework.c02nio;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by hjs on 2021/5/17.
 */
public class OkHttpUtils {

    /**
     * 缓存客户端实例
     */
    public static OkHttpClient client = new OkHttpClient();

    /**
     * GET 调用
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getAsString(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "https://localhost:8081";
        String text = OkHttpUtils.getAsString(url);
        System.out.println("url: " + url + " ; response: \n" + text);

        // 清理资源
        OkHttpUtils.client = null;
    }
}