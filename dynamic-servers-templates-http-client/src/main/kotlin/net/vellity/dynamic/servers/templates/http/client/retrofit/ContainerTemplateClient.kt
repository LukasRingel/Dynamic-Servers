package net.vellity.dynamic.servers.templates.http.client.retrofit

import net.vellity.dynamic.servers.templates.http.client.ContainerTemplateInformation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ContainerTemplateClient {
  @GET("/api/templates")
  fun getTemplateWithId(@Query("containerTemplateId") containerTemplateId: String):
    Call<ContainerTemplateInformation?>
}