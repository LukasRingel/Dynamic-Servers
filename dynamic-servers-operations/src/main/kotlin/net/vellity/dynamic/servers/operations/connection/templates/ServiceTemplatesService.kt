package net.vellity.dynamic.servers.operations.connection.templates

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.templates.http.client.ContainerTemplateInformation
import net.vellity.dynamic.servers.templates.http.client.TemplatesClient
import net.vellity.dynamic.servers.templates.http.client.retrofit.RetrofitTemplatesClient
import org.springframework.stereotype.Service

@Service
class ServiceTemplatesService : TemplatesService {
  private var templateClient: TemplatesClient = RetrofitTemplatesClient(
    sourceName = "operations",
    apiKey = environmentOrDefault("TEMPLATE_SERVER_API_KEY", "default-api-key"),
    hostname = environmentOrDefault("TEMPLATE_SERVER_HOSTNAME", "http://localhost:8081")
  )

  override fun getContainerTemplate(template: String): ContainerTemplateInformation? {
    return templateClient.getContainerTemplateInformation(template)
  }

  override fun updateTemplatesAndClearCache() {
    templateClient.updateTemplatesAndClearCache()
  }
}