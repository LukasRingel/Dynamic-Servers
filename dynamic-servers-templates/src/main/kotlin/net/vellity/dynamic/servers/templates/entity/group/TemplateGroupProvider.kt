package net.vellity.dynamic.servers.templates.entity.group

interface TemplateGroupProvider {
  fun getGroupsOfTemplate(templateId: String): List<TemplateGroup>

  fun refresh()
}