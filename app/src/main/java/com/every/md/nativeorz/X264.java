package com.every.md.nativeorz;

/**
 * Created by Yunga on 2017/9/4.
 */

public class X264 {

    static {
        System.loadLibrary("mine");
    }

    public static native String Encoder(byte[] bytes, int width, int height);

    public static native String CloseEncoder();

    public static native void test();
}
