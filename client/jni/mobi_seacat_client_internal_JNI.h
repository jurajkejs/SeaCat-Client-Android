/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class mobi_seacat_client_internal_JNI */

#ifndef _Included_mobi_seacat_client_internal_JNI
#define _Included_mobi_seacat_client_internal_JNI
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     mobi_seacat_client_internal_JNI
 * Method:    seacat_reactor_init
 * Signature: (Lmobi/seacat/client/internal/Reactor;)I
 */
JNIEXPORT jint JNICALL Java_mobi_seacat_client_internal_JNI_seacat_1reactor_1init
  (JNIEnv *, jclass, jobject);

/*
 * Class:     mobi_seacat_client_internal_JNI
 * Method:    seacat_reactor_fini
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_mobi_seacat_client_internal_JNI_seacat_1reactor_1fini
  (JNIEnv *, jclass);

/*
 * Class:     mobi_seacat_client_internal_JNI
 * Method:    seacat_reactor_run
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_mobi_seacat_client_internal_JNI_seacat_1reactor_1run
  (JNIEnv *, jclass);

/*
 * Class:     mobi_seacat_client_internal_JNI
 * Method:    seacat_reactor_shutdown
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_mobi_seacat_client_internal_JNI_seacat_1reactor_1shutdown
  (JNIEnv *, jclass);

/*
 * Class:     mobi_seacat_client_internal_JNI
 * Method:    seacat_reactor_yield
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_mobi_seacat_client_internal_JNI_seacat_1reactor_1yield
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
