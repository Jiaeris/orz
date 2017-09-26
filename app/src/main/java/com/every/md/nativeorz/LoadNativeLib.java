package com.every.md.nativeorz;

/**
 * Created by Yunga on 2017/9/4.
 */

public class LoadNativeLib {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String helloCpp(byte[] bytes, int width, int height);

    public static native String x264EncoderClose();

    public static native void test();
}
