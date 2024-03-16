package net.vellity.dynamic.servers.templates.entity.group

import org.springframework.stereotype.Service

@Service
class CachedTemplateGroupProvider(private val templateGroupRepository: TemplateGroupRepository) :
  TemplateGroupProvider {
  private var templateGroups: List<TemplateGroup> = templateGroupRepository.findAll()

  override fun getGroupsOfTemplate(templateId: String): List<TemplateGroup> {
    return templateGroupRepository.findAll().filter {
      it.templates.any { template -> template.id == templateId }
    }
  }

  override fun refresh() {
    templateGroups = templateGroupRepository.findAll()
  }
}