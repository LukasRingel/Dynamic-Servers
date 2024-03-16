package net.vellity.dynamic.servers.templates.entity.group

import net.vellity.dynamic.servers.templates.entity.template.ContainerTemplate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Reference
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "containerTemplateGroups")
class TemplateGroup {
  @Id
  var id: String? = null

  @Reference
  var templates = mutableListOf<ContainerTemplate>()

  var environment: Map<String, String> = mutableMapOf()

  var tags = mutableListOf<String>()

  var pathsToCopy = mutableListOf<String>()
}