package com.every.md.encoder;

/**
 * Created by Yunga on 2017/9/5.
 */

public interface H264EncoderListener {
    void onH264Frame(byte[] h264Data);
}
