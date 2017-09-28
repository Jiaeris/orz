//
// Created by Yunga on 2017/9/4.
//
#include "native-lib.h"
#include "../include/libx264/x264.h"
x264_t *x264 = NULL;
int isInit = 0;


JNIEXPORT jstring JNICALL
Java_com_every_md_nativeorz_LoadNativeLib_helloCpp(JNIEnv *env,
                                           jclass type,
                                           jbyteArray bytes_,
                                           jint width,
                                           jint height) {
    jstring return_str = (*env)->NewStringUTF(env, STR);
    jbyte *bytes = (*env)->GetByteArrayElements(env, bytes_, NULL);

    // TODO
    if (isInit == 0) {
        init_X264(10, height, width, 70);
        if (x264 == NULL) {
            LOG_E("init x264 failed .");
            return return_str;
        }
    }

    x264_picture_t x264_picture;
    x264_picture.img.i_csp = X264_CSP_I420;
    x264_picture.img.i_plane = 3;
    x264_picture.img.i_stride[0] = width * height;
    x264_picture.img.i_stride[1] = width * height / 4;
    x264_picture.img.i_stride[2] = width * height / 4;
    void *Y = malloc(width * height);
    void *U = malloc(width * height / 4);
    void *V = malloc(width * height / 4);
    x264_picture.img.plane[0] = Y;
    x264_picture.img.plane[1] = U;
    x264_picture.img.plane[2] = V;
    memcpy(Y, bytes, width * height);
    memcpy(U, bytes + width * height, width * height / 4);
    memcpy(V, bytes + width * height + width * height / 4, width * height / 4);

    x264_nal_t *nalOut;
    int nalNum;
    x264_picture_t g_x264pic_out;
    x264_encoder_encode(x264, &nalOut, &nalNum, &x264_picture, &g_x264pic_out);

    free(Y);
    free(U);
    free(V);
    free(nalOut);


    (*env)->ReleaseByteArrayElements(env, bytes_, bytes, 0);

    return return_str;
}

JNIEXPORT void JNICALL
Java_com_every_md_nativeorz_LoadNativeLib_x264EncoderClose(JNIEnv *env, jclass type) {
    if (x264 != NULL) {
        x264_encoder_close(x264);
        x264 = NULL;
    }
}

void init_X264(int fps, int width, int height, int quality) {
    x264_param_t x264_param;
    x264_param_default_preset(&x264_param, "veryfast", "zerolatency");
    x264_param.i_width = width;
    x264_param.i_height = height;
    x264_param.i_frame_total = 0;
    x264_param.i_fps_num = fps;
    x264_param.i_fps_den = 1;
    x264_param.rc.f_rf_constant = quality;
    x264 = x264_encoder_open(&x264_param);
    isInit = 1;
}
