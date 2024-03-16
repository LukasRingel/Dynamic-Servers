package net.vellity.dynamic.servers.server.watcher.http.client

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitWatcherOperationsRoutes {
  @POST("/api/operations/restart")
  fun restartServer(@Query("serverId") serverId: String): Call<Unit>
}