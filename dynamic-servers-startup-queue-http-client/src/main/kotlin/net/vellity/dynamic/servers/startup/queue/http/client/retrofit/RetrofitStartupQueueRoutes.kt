package net.vellity.dynamic.servers.startup.queue.http.client.retrofit

import net.vellity.dynamic.servers.startup.queue.http.client.dto.StartupQueueAddEntryDto
import net.vellity.dynamic.servers.startup.queue.http.client.dto.StartupQueueEntryDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface RetrofitStartupQueueRoutes {
  @GET("/api/queue/next")
  fun pollNextServerToStart(): Call<StartupQueueEntryDto?>

  @GET("/api/queue")
  fun getAllServersToStart(): Call<List<StartupQueueEntryDto>>

  @POST("/api/queue")
  fun addServerToStart(@Body startupQueueAddEntryDto: StartupQueueAddEntryDto): Call<UUID>

  @DELETE("/api/queue/all")
  fun deleteAllEntries(): Call<Unit>
}