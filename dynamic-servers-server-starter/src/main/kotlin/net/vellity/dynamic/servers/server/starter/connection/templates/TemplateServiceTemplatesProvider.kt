package net.vellity.dynamic.servers.server.starter.connection.templates

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.templates.http.client.ContainerTemplateInformation
import net.vellity.dynamic.servers.templates.http.client.TemplatesClient
import net.vellity.dynamic.servers.templates.http.client.retrofit.RetrofitTemplatesClient
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class TemplateServiceTemplatesProvider : TemplatesProvider {
  private var templateClient: TemplatesClient = RetrofitTemplatesClient(
    sourceName = environmentOrDefault("SERVER_STARTER_NAME", "local"),
    apiKey = environmentOrDefault("TEMPLATE_SERVER_API_KEY", "default-api-key"),
    hostname = environmentOrDefault("TEMPLATE_SERVER_HOSTNAME", "http://localhost:8081")
  )

  override fun downloadFilesOfTemplate(containerTemplateId: String): InputStream =
    templateClient.downloadFilesOfTemplate(containerTemplateId)

  override fun getContainerTemplateInformation(containerTemplateId: String): ContainerTemplateInformation? =
    templateClient.getContainerTemplateInformation(containerTemplateId)
}