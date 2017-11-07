#include <jni.h>
#include <android/log.h>
#include <string.h>
#include <malloc.h>
#include "../include/libx264/x264.h"

#define LOG_D(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, "mine", __VA_ARGS__))
#define LOG_I(...) ((void)__android_log_print(ANDROID_LOG_INFO , "mine", __VA_ARGS__))
#define LOG_W(...) ((void)__android_log_print(ANDROID_LOG_WARN , "mine", __VA_ARGS__))
#define LOG_E(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "mine", __VA_ARGS__))

#define STR "hello native lib"

void init_X264(int fps, int width, int height, int quality);