package com.every.md.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunga on 2017/9/25.
 */

public class HttpClient {


    private static final String TAG = "[HttpClient]";

    public static void GET(String url, HttpListener callback) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(8000);
            urlConnection.setReadTimeout(8000);
            Map<String, List<String>> headerMap = urlConnection.getHeaderFields();
            for (Map.Entry<String, List<String>> head : headerMap.entrySet()) {
                String key = head.getKey();
                String value = "";
                for (String v : head.getValue()) {
                    value = value + v;
                }
                String header = String.format("key:%s values:%s", key, value);
                Log.w(TAG, "GET: " + header);
            }

            InputStream is = urlConnection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
                baos.flush();
            }
            String response = baos.toString("utf-8");
            callback.success(response);
        } catch (IOException e) {
            callback.fail(e.getMessage());
        }

    }

    /**
     * @param url
     * @param post     post = xx=xx&yy=yy
     * @param callback
     */
    public static void POST(String url, String post, HttpListener callback) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(8000);
            urlConnection.setReadTimeout(8000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            //输出
            OutputStream os = urlConnection.getOutputStream();
            os.write((post.getBytes()));
            os.flush();
            //输入
            InputStream is = urlConnection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
                baos.flush();
            }
            String response = baos.toString("utf-8");
            System.out.println(response);
            callback.success(response);
        } catch (IOException e) {
            callback.fail(e.getMessage());
        }
    }
}
