package net.vellity.dynamic.servers.startup.queue.http.client.retrofit

import net.vellity.dynamic.servers.common.http.client.OkHttpAuthenticationAndSourceNameInterceptor
import net.vellity.dynamic.servers.startup.queue.http.client.StartupQueueClient
import net.vellity.dynamic.servers.startup.queue.http.client.dto.StartupQueueAddEntryDto
import net.vellity.dynamic.servers.startup.queue.http.client.dto.StartupQueueEntryDto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitStartupQueueClient(sourceName: String, apiKey: String, baseUrl: String) : StartupQueueClient {
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
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

  private val startupQueueRoutes = jsonRetrofit.create(RetrofitStartupQueueRoutes::class.java)

  override fun pollNextServerToStart(): StartupQueueEntryDto? {
    return startupQueueRoutes.pollNextServerToStart().execute().body()
  }

  override fun getAllServersToStart(): List<StartupQueueEntryDto> {
    return startupQueueRoutes.getAllServersToStart().execute().body() ?: emptyList()
  }

  override fun addServerToStart(templateId: String, priority: Int, environment: Map<String, String>): UUID? {
    return startupQueueRoutes.addServerToStart(
      StartupQueueAddEntryDto(
        templateId = templateId,
        priority = priority,
        environment = environment
      )
    ).execute().body()
  }

  override fun deleteAllEntries() {
    startupQueueRoutes.deleteAllEntries().execute()
  }
}