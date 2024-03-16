package net.vellity.dynamic.servers.templates.entity.template

interface ContainerTemplateProvider {
  fun getContainerTemplateById(containerTemplateId: String): ContainerTemplate?

  fun saveOrUpdateContainerTemplate(containerTemplate: ContainerTemplate)

  fun refresh()
}