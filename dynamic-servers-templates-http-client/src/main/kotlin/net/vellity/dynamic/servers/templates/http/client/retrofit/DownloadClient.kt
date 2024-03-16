package net.vellity.dynamic.servers.templates.http.client.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming

interface DownloadClient {
  @Streaming
  @GET("/api/download")
  fun download(@Query("containerTemplateId") containerTemplateId: String): Call<ResponseBody>
}