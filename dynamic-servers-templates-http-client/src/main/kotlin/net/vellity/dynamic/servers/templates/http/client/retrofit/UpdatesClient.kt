package net.vellity.dynamic.servers.templates.http.client.retrofit

import retrofit2.Call
import retrofit2.http.POST

interface UpdatesClient {
  @POST("/api/updates")
  fun update(): Call<Unit>
}