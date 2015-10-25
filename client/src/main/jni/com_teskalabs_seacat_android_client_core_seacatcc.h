/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_teskalabs_seacat_android_client_core_seacatcc */

#ifndef _Included_com_teskalabs_seacat_android_client_core_seacatcc
#define _Included_com_teskalabs_seacat_android_client_core_seacatcc
#ifdef __cplusplus
extern "C" {
#endif
#undef com_teskalabs_seacat_android_client_core_seacatcc_RC_OK
#define com_teskalabs_seacat_android_client_core_seacatcc_RC_OK 0L
#undef com_teskalabs_seacat_android_client_core_seacatcc_RC_E_GENERIC
#define com_teskalabs_seacat_android_client_core_seacatcc_RC_E_GENERIC -9999L
/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    init
 * Signature: (Ljava/lang/String;Ljava/lang/String;Lcom/teskalabs/seacat/android/client/core/Reactor;)I
 */
JNIEXPORT jint JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_init
  (JNIEnv *, jclass, jstring, jstring, jobject);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    run
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_run
  (JNIEnv *, jclass);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    shutdown
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_shutdown
  (JNIEnv *, jclass);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    yield
 * Signature: (C)I
 */
JNIEXPORT jint JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_yield
  (JNIEnv *, jclass, jchar);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    state
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_state
  (JNIEnv *, jclass);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    ppkgen_worker
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_ppkgen_1worker
  (JNIEnv *, jclass);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    csrgen_worker
 * Signature: ([Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_csrgen_1worker
  (JNIEnv *, jclass, jobjectArray);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    cacert_url
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_cacert_1url
  (JNIEnv *, jclass);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    cacert_worker
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_cacert_1worker
  (JNIEnv *, jclass, jbyteArray);

/*
 * Class:     com_teskalabs_seacat_android_client_core_seacatcc
 * Method:    time
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_teskalabs_seacat_android_client_core_seacatcc_time
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif