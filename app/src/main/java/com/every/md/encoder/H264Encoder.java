package com.every.md.encoder;

import android.hardware.Camera;
import android.util.Log;

import com.every.md.nativeorz.X264;
import com.github.faucamp.simplertmp.DefaultRtmpPublisher;
import com.github.faucamp.simplertmp.RtmpHandler;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by Yunga on 2017/9/4.
 */

public class H264Encoder implements Camera.PreviewCallback {

    private static final String TAG = "[H264Encoder]";
    byte[] h264;

    public H264Encoder(Camera.Size preview_size) {
        h264 = new byte[preview_size.width * preview_size.height * 3 / 2];
    }

    @Override
    public void onPreviewFrame(byte[] yuv420sp, Camera camera) {
        Camera.Size preview_size = camera.getParameters().getPreviewSize();
        int width = preview_size.width;
        int height = preview_size.height;

        byte[] yuv420 = new byte[width * height * 3 / 2];

        X264.Encoder(yuv420sp, width, height);

        Log.w(TAG, "onPreviewFrame: " + yuv420sp.length);
    }


}
