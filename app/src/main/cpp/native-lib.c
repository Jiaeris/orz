//
// Created by Yunga on 2017/9/4.
//
#include <jni.h>
#include <android/log.h>
#include "libx264/x264.h"

char *tag = "LiveActivity";
char *fmt = "%s";
char *str = "hello c";

JNIEXPORT jstring JNICALL
Java_com_every_md_encoder_LoadLib_helloCpp(JNIEnv *env,
                                           jclass type,
                                           jbyteArray bytes_,
                                           jint width,
                                           jint height) {
    jbyte *bytes = (*env)->GetByteArrayElements(env, bytes_, NULL);

//    x264_param_t t;
//    t.i_width = width;
//    t.i_height = height;
    


    (*env)->ReleaseByteArrayElements(env, bytes_, bytes, 0);
    return (*env)->NewStringUTF(env, str);
}