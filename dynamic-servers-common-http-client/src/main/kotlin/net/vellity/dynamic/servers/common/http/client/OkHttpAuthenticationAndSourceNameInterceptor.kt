package net.vellity.dynamic.servers.common.http.client

import okhttp3.Interceptor
import okhttp3.Response

class OkHttpAuthenticationAndSourceNameInterceptor(
  private val sourceName: String,
  private val apiKey: String
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val newRequest = request.newBuilder()
      .url(request.url())
      .addHeader(SOURCE_NAME_HEADER, sourceName)
      .addHeader(API_KEY_HEADER, apiKey)
      .build()
    return chain.proceed(newRequest)
  }

  companion object {
    private const val SOURCE_NAME_HEADER = "X-Source-Name"
    private const val API_KEY_HEADER = "X-API-KEY"
  }
}