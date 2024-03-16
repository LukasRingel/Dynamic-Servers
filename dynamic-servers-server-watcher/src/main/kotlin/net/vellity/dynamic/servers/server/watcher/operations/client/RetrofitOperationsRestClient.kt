package net.vellity.dynamic.servers.server.watcher.operations.client

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.registry.http.client.OkHttpAuthenticationAndSourceNameInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitOperationsRestClient : OperationsRestClient {
  private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .readTimeout(5, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.SECONDS)
    .addInterceptor(
      OkHttpAuthenticationAndSourceNameInterceptor(
        sourceName = environmentOrDefault("SERVER_STARTER_NAME", "local"),
        apiKey = environmentOrDefault("OPERATIONS_SERVER_API_KEY", "default-api-key")
      )
    )
    .build()

  private val jsonRetrofit = Retrofit.Builder()
    .baseUrl(environmentOrDefault("OPERATIONS_SERVER_HOSTNAME", "http://localhost:8080"))
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

  private val watcherRegistryRoutes = jsonRetrofit.create(OperationWatcherRegistryRoutes::class.java)

  override fun registerToOperations() {
    watcherRegistryRoutes.registerWatcher(
      WatcherRegistrationDto(
        executorId = executorId,
        hostname = environmentOrDefault("SERVER_WATCHER_HOSTNAME", "http://localhost"),
        port = environmentOrDefault("SERVER_PORT", "8091").toInt(),
        apiKey = environmentOrDefault("API_KEY", "default-api-key")
      )
    ).execute()
  }

  override fun heartbeat() {
    watcherRegistryRoutes.heartbeat().execute()
  }

  companion object {
    private val executorId = environmentOrDefault("SERVER_STARTER_NAME", "local")
  }
}