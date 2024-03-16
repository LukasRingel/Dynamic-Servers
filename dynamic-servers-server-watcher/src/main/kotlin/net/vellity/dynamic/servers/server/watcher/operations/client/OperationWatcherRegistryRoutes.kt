package net.vellity.dynamic.servers.server.watcher.operations.client

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface OperationWatcherRegistryRoutes {
  @POST("/api/watcher")
  fun registerWatcher(@Body registration: WatcherRegistrationDto): Call<Unit>

  @PUT("/api/watcher")
  fun heartbeat(): Call<Unit>
}