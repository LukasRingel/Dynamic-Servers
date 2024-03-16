package net.vellity.dynamic.servers.operations.connection.templates

import net.vellity.dynamic.servers.templates.http.client.ContainerTemplateInformation

interface TemplatesService {
  fun getContainerTemplate(template: String): ContainerTemplateInformation?

  fun updateTemplatesAndClearCache()
}