package net.vellity.dynamic.servers.log.storage.http.client.retrofit

import net.vellity.dynamic.servers.log.storage.http.client.dto.DatabaseStatsDto
import net.vellity.dynamic.servers.log.storage.http.client.dto.StoreLogRequestDto
import net.vellity.dynamic.servers.log.storage.http.client.dto.StoredLogDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface LogStorageRoutes {
  @POST("/api/storage")
  fun storeLog(@Body request: StoreLogRequestDto): Call<Unit>

  @GET("/api/storage")
  fun getLogData(@Query("serverId") serverId: String): Call<StoredLogDto>

  @GET("/api/storage/database")
  fun getDatabaseStats(): Call<DatabaseStatsDto>
}