//
// Created by Yunga on 2017/9/5.
//

#include <jni.h>
#include "test.h"
#include "android/log.h"


extern "C" {

void Test::helloCpp() {
    __android_log_print(ANDROID_LOG_DEBUG, "hello cpp", "debug", "%s", "HelloCpp()");
}

Test::Test() {

}

JNIEXPORT void JNICALL
Java_com_every_md_nativeorz_X264_test(JNIEnv *env,
                                               jclass type) {
    Test::helloCpp();
}
}

