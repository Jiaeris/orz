package com.every.md.json;

import com.google.gson.Gson;

/**
 * Created by Yunga on 2017/9/25.
 */

public class GsonUtil {
    private static Gson gson = new Gson();
    private static GsonUtil gsonUtil = new GsonUtil();

    public static GsonUtil instance() {
        return gsonUtil;
    }

    public String o2j(Object o) {
        return gson.toJson(o);
    }

    public <T> T j2o(String s, Class<T> t) {
        return gson.fromJson(s, t);
    }

}
