//
// Created by Yunga on 2017/9/4.
//
#include "native-lib.h"
#include "../include/libx264/x264.h"

#define FALSE 0
#define TRUE 1

x264_t *x264;
int isInit = FALSE;


JNIEXPORT jstring JNICALL
Java_com_every_md_nativeorz_X264_Encoder(JNIEnv *env,
                                         jclass type,
                                         jbyteArray bytes_,
                                         jint width,
                                         jint height) {
    jbyte *bytes = (*env)->GetByteArrayElements(env, bytes_, 0);

    // TODO
    if (!isInit) {
        init_X264(10, height, width, 80);
        if (x264 == NULL) {
            LOG_E("init X264 failed .");
            return "";
        }
    }

    x264_picture_t *pic_in = malloc(sizeof(x264_picture_t));
    pic_in->img.i_csp = X264_CSP_NV21;
    pic_in->img.i_plane = 3;
    pic_in->img.i_stride[0] = width * height;
    pic_in->img.i_stride[1] = width * height / 4;
    pic_in->img.i_stride[2] = width * height / 4;
//    jbyte *Y = malloc(width * height);
//    jbyte *U = malloc(width * height / 4);
//    jbyte *V = malloc(width * height / 4);
//    pic_in->img.plane[0] = Y;
//    pic_in->img.plane[1] = U;
//    pic_in->img.plane[2] = V;
    memcpy(pic_in->img.plane[0], bytes, width * height);
    memcpy(pic_in->img.plane[1], bytes + width * height, width * height / 4);
    memcpy(pic_in->img.plane[2], bytes + width * height + width * height / 4, width * height / 4);

    x264_nal_t *nalOut;
    int nalNum = 0;
    x264_picture_t pic_out;

    int iResult;
    //???
    iResult = x264_encoder_encode(x264, &nalOut, &nalNum, pic_in, &pic_out);
    if (0 == iResult) {
        LOG_W("编码成功,但被缓存了");
    } else if (iResult < 0) {
        LOG_W("编码出错");
    } else if (iResult > 0) {
        LOG_W(" 得到编码数据");
    }
//    free(Y);
//    free(U);
//    free(V);
    free(pic_in);

    (*env)->ReleaseByteArrayElements(env, bytes_, bytes, 0);
    return (*env)->NewStringUTF(env, STR);
}

JNIEXPORT void JNICALL
Java_com_every_md_nativeorz_X264_CloseEncoder(JNIEnv *env, jclass type) {
    if (x264 != NULL) {
        x264_encoder_close(x264);
        x264 = NULL;
        isInit = FALSE;
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
    x264_param.i_threads = X264_SYNC_LOOKAHEAD_AUTO;

    x264 = x264_encoder_open(&x264_param);
    isInit = TRUE;
}
