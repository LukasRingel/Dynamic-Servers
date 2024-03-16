package net.vellity.dynamic.servers.templates.entity.template

import org.springframework.stereotype.Service

@Service
class LocalContainerTemplateProvider(
  private val containerTemplateRepository: ContainerTemplateRepository
) : ContainerTemplateProvider {
  private val templates = mutableMapOf<String, ContainerTemplate>()

  init {
    loadAll()
  }

  override fun getContainerTemplateById(containerTemplateId: String): ContainerTemplate? {
    return templates[containerTemplateId]
  }

  override fun saveOrUpdateContainerTemplate(containerTemplate: ContainerTemplate) {
    templates[containerTemplate.id!!] = containerTemplate
    containerTemplateRepository.save(containerTemplate)
  }

  override fun refresh() {
    templates.clear()
    loadAll()
  }

  private fun loadAll() {
    containerTemplateRepository.findAll().forEach {
      templates[it.id!!] = it
    }
  }
}