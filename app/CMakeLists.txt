# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add.library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

#file(GLOB native_src "src/main/cpp/*.c")

add_library( # Specifies the name of the library.
             mine

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/liborz/native-lib.c
             src/main/cpp/liborz/test.cpp)

find_library( # Defines the name of the path variable that stores the
             # location of the NDK library.
             log-lib

             # Specifies the name of the NDK library that
             # CMake needs to locate.
             log )

# 关联自己的库和原生库
target_link_libraries( # Specifies the target library.
                      mine
                      libx264
                      # Links the log library to the target library.
                      ${log-lib})
#添加头目录
include_directories(src/main/cpp/include/libx264)
add_library(libx264
            STATIC
            IMPORTED)
set_target_properties(libx264
                      PROPERTIES IMPORTED_LOCATION
                      ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libx264.a)