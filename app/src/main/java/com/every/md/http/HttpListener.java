package com.every.md.http;

/**
 * Created by Yunga on 2017/9/25.
 */

public interface HttpListener {
    void success(String response);

    void fail(String error);
}
