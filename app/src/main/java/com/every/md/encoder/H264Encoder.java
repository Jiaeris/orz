package com.every.md.encoder;

import android.hardware.Camera;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by Yunga on 2017/9/4.
 */

public class H264Encoder implements Camera.PreviewCallback {

    //这里是为了发送视频到vlc客户端进行测试。
    private InetAddress address;
    private DatagramSocket socket;
    private UdpSendTask netSendTask;

    private static final String TAG = "[H264Encoder]";
    private AvcEncoder avcEncoder;
    byte[] h264;

    public H264Encoder(Camera.Size preview_size) {
        avcEncoder = new AvcEncoder(preview_size.width, preview_size.height, 10, 125000);
        h264 = new byte[preview_size.width * preview_size.height * 3 / 2];

        //-------------启动发送数据线程-----------------
        netSendTask = new UdpSendTask();
        netSendTask.init();
        netSendTask.start();
    }

    @Override
    public void onPreviewFrame(byte[] yuv420sp, Camera camera) {
//        Camera.Size preview_size = camera.getParameters().getPreviewSize();
//        int width = preview_size.width;
//        int height = preview_size.height;
//
//        byte[] yuv420 = new byte[width * height * 3 / 2];
//        YUV420SP2YUV420(yuv420sp, yuv420, width, height);

        int ret = avcEncoder.offerEncoder(yuv420sp, h264);
        if (ret > 0) {
            //发送h264到vlc
            Log.d(TAG, "h264: " + h264.length);
            netSendTask.pushBuf(h264, ret);
        }
        Log.d(TAG, "ret: " + ret);
    }

    public void x264_encoder_close() {
        LoadLib.x264EncoderClose();
    }

    //发送数据的线程
    class UdpSendTask extends Thread {
        private ArrayList<ByteBuffer> mList;

        public void init() {
            try {
                socket = new DatagramSocket();
                //设置IP
                address = InetAddress.getByName("192.168.0.110");
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            mList = new ArrayList<ByteBuffer>();

        }

        //添加数据
        public void pushBuf(byte[] buf, int len) {
            ByteBuffer buffer = ByteBuffer.allocate(len);
            buffer.put(buf, 0, len);
            mList.add(buffer);
        }

        @Override
        public void run() {
            Log.d(TAG, "fall in udp send thread");
            while (true) {
                if (mList.size() <= 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while (mList.size() > 0) {
                    ByteBuffer sendBuf = mList.get(0);
                    try {

                        //发送数据到指定地址
                        Log.d(TAG, "send udp packet len:" + sendBuf.capacity());
                        DatagramPacket packet = new DatagramPacket(sendBuf.array(), sendBuf.capacity(), address, 5000);

                        socket.send(packet);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    //移除已经发送的数据
                    mList.remove(0);
                }
            }
        }
    }

}
