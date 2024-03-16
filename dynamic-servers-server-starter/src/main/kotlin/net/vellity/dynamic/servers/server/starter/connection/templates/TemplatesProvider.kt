package net.vellity.dynamic.servers.server.starter.connection.templates

import net.vellity.dynamic.servers.templates.http.client.ContainerTemplateInformation
import java.io.InputStream

interface TemplatesProvider {
  fun downloadFilesOfTemplate(containerTemplateId: String): InputStream

  fun getContainerTemplateInformation(containerTemplateId: String): ContainerTemplateInformation?
}