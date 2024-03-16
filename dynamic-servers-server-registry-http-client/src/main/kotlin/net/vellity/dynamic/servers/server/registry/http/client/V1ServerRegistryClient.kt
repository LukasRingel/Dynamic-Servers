package net.vellity.dynamic.servers.server.registry.http.client

import net.vellity.dynamic.servers.server.registry.http.client.dto.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class V1ServerRegistryClient(hostname: String, apiKey: String, sourceName: String) : ServerRegistryClient {
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

  private val retrofit = Retrofit.Builder()
    .baseUrl(hostname)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

  private val routes = retrofit.create(ServerRegistryClientRoutes::class.java)

  override fun createServer(
    serverId: String,
    template: String,
    port: Int,
    hostname: String,
    tags: List<String>,
    additionalEnvironmentVars: Map<String, String>
  ) {
    routes.createServer(
      CreateServerDto(
        serverId = serverId,
        template = template,
        port = port,
        hostname = hostname,
        tags = tags,
        additionalEnvironmentVars = additionalEnvironmentVars
      )
    ).execute()
  }

  override fun deleteServer(serverId: String, reason: String) {
    routes.deleteServer(serverId, reason).execute()
  }

  override fun updateServerStatus(serverId: String, status: ServerStatus) {
    routes.updateServerStatus(
      UpdateServerStatusDto(
        serverId = serverId,
        status = status
      )
    ).execute()
  }

  override fun addTagsToServer(serverId: String, tag: String) {
    routes.addTagsToServer(
      AddTagToServerDto(
        serverId = serverId,
        tag = tag
      )
    ).execute()
  }

  override fun allServers(): List<RegisteredServerDto> {
    return routes.allServers().execute().body()!!
  }

  override fun allServersOfTemplate(template: String): List<RegisteredServerDto> {
    return routes.allServersOfTemplate(template).execute().body()!!
  }

  override fun allServersOnExecutor(executor: String): List<RegisteredServerDto> {
    return routes.allServersOnExecutor(executor).execute().body()!!
  }

  override fun allServersWithoutTag(tags: String): List<RegisteredServerDto> {
    return routes.allServersWithoutTag(tags).execute().body()!!
  }

  override fun getServerHistory(serverId: String): ServerHistoryDto {
    return routes.getServerHistory(serverId).execute().body()!!
  }

  override fun findHistories(
    limit: Int,
    minDate: Long,
    maxDate: Long,
    template: String,
    executor: String
  ): List<ServerHistoryDto> {
    return routes.findHistories(limit, minDate, maxDate, template, executor).execute().body()!!
  }
}