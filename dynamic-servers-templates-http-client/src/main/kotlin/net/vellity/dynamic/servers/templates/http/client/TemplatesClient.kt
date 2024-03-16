package net.vellity.dynamic.servers.templates.http.client

import java.io.InputStream

interface TemplatesClient {
  fun downloadFilesOfTemplate(containerTemplateId: String): InputStream

  fun getContainerTemplateInformation(containerTemplateId: String): ContainerTemplateInformation?

  fun updateTemplatesAndClearCache()
}