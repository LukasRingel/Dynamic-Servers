package net.vellity.dynamic.servers.server.registry.http.client

import net.vellity.dynamic.servers.server.registry.http.client.dto.*
import retrofit2.Call
import retrofit2.http.*

interface ServerRegistryClientRoutes {
  @POST("/api/servers")
  fun createServer(@Body createServerDto: CreateServerDto): Call<Unit>

  @DELETE("/api/servers")
  fun deleteServer(
    @Query("serverId") serverId: String,
    @Query("reason") reason: String
  ): Call<Unit>

  @PUT("/api/servers/status")
  fun updateServerStatus(@Body updateServerStatusDto: UpdateServerStatusDto): Call<Unit>

  @PUT("api/servers/tags")
  fun addTagsToServer(
    @Body addTagToServerDto: AddTagToServerDto
  ): Call<Unit>

  @GET("/api/servers")
  fun allServers(): Call<List<RegisteredServerDto>>

  @GET("/api/servers/template")
  fun allServersOfTemplate(@Query("template") template: String): Call<List<RegisteredServerDto>>

  @GET("/api/servers/executor")
  fun allServersOnExecutor(@Query("executor") executor: String): Call<List<RegisteredServerDto>>

  @GET("/api/servers/withoutTags")
  fun allServersWithoutTag(@Query("tags") tags: String): Call<List<RegisteredServerDto>>

  @GET("/api/history")
  fun getServerHistory(@Query("serverId") serverId: String): Call<ServerHistoryDto>

  @GET("/api/history/find")
  fun findHistories(
    @Query("limit") limit: Int,
    @Query("minDate") minDate: Long,
    @Query("maxDate") maxDate: Long,
    @Query("template") template: String,
    @Query("executor") executor: String
  ): Call<List<ServerHistoryDto>>
}