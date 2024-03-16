package net.vellity.dynamic.servers.templates.http.client.retrofit

import net.vellity.dynamic.servers.common.http.client.OkHttpAuthenticationAndSourceNameInterceptor
import net.vellity.dynamic.servers.templates.http.client.ContainerTemplateInformation
import net.vellity.dynamic.servers.templates.http.client.TemplatesClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.concurrent.TimeUnit

class RetrofitTemplatesClient(sourceName: String, apiKey: String, hostname: String) : TemplatesClient {
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

  private val rawRetrofit = Retrofit.Builder()
    .baseUrl(hostname)
    .client(okHttpClient)
    .build()

  private val jsonRetrofit = Retrofit.Builder()
    .baseUrl(hostname)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

  private val download: DownloadClient = rawRetrofit.create(DownloadClient::class.java)
  private val containerTemplate: ContainerTemplateClient = jsonRetrofit.create(ContainerTemplateClient::class.java)
  private val updatesClient: UpdatesClient = jsonRetrofit.create(UpdatesClient::class.java)

  override fun downloadFilesOfTemplate(containerTemplateId: String): InputStream {
    val call = download.download(containerTemplateId).execute()
    if (!call.isSuccessful || call.body() == null) {
      return ByteArrayInputStream(ByteArray(0))
    }
    return ByteArrayInputStream(call.body()!!.bytes())
  }

  override fun getContainerTemplateInformation(containerTemplateId: String): ContainerTemplateInformation? {
    val call = containerTemplate.getTemplateWithId(containerTemplateId).execute()
    if (!call.isSuccessful || call.body() == null) {
      return null
    }
    return call.body()
  }

  override fun updateTemplatesAndClearCache() {
    updatesClient.update().execute()
  }
}