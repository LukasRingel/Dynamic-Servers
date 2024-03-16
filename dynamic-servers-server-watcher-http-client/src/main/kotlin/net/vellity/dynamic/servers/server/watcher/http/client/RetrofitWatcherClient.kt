package net.vellity.dynamic.servers.server.watcher.http.client

import net.vellity.dynamic.servers.common.http.client.OkHttpAuthenticationAndSourceNameInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitWatcherClient(sourceName: String, apiKey: String, baseUrl: String) : WatcherClient {
  private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .readTimeout(5, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.SECONDS)
    .addInterceptor(
      OkHttpAuthenticationAndSourceNameInterceptor(
        sourceName = sourceName,
        apiKey = apiKey
      )
    )
    .build()

  private val jsonRetrofit = Retrofit.Builder()
    .baseUrl(baseUrl.endsWith("/").let { if (it) baseUrl else "$baseUrl/" })
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

  private val operationsRoutes = jsonRetrofit.create(RetrofitWatcherOperationsRoutes::class.java)

  override fun stopServer(serverId: String) {
    operationsRoutes.restartServer(serverId).execute()
  }
}