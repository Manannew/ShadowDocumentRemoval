#include <jni.h>
#include <string>
#include "ShadowRemover.h"
#include <cstdio>
#include <ctime>

using namespace std;

extern "C"

JNIEXPORT void JNICALL Java_com_example_myapplication4_NativeClass_removeShadow(JNIEnv *env, jclass, jstring path){


    const  char* inputImage =  env->GetStringUTFChars(path,0);

    ShadowRemover* sr = new ShadowRemover((char *) inputImage);
    sr->RemoveShadow((char *) "/storage/emulated/0/Output/");

    delete sr;

}

