package net.vellity.dynamic.servers.log.storage.http.client.retrofit

import net.vellity.dynamic.servers.common.http.client.OkHttpAuthenticationAndSourceNameInterceptor
import net.vellity.dynamic.servers.log.storage.http.client.LogStorageClient
import net.vellity.dynamic.servers.log.storage.http.client.dto.DatabaseStatsDto
import net.vellity.dynamic.servers.log.storage.http.client.dto.StoreLogRequestDto
import net.vellity.dynamic.servers.log.storage.http.client.dto.StoredLogDto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitLogStorageClient(sourceName: String, apiKey: String, hostname: String): LogStorageClient {
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
    .baseUrl(hostname)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

  private val logStorageRoutes = jsonRetrofit.create(LogStorageRoutes::class.java)

  override fun storeLog(name: String, content: ByteArray) {
    logStorageRoutes.storeLog(StoreLogRequestDto(name, content)).execute()
  }

  override fun getLogData(serverId: String): StoredLogDto? {
    val response = logStorageRoutes.getLogData(serverId).execute()
    return if (response.isSuccessful) response.body() else null
  }

  override fun getDatabaseStats(): DatabaseStatsDto {
    return logStorageRoutes.getDatabaseStats().execute().body()!!
  }
}