package net.vellity.dynamic.servers.templates.entity.rest

import net.vellity.dynamic.servers.templates.entity.group.TemplateGroup
import net.vellity.dynamic.servers.templates.entity.group.TemplateGroupProvider
import net.vellity.dynamic.servers.templates.entity.rest.dto.ContainerTemplateDto
import net.vellity.dynamic.servers.templates.entity.template.ContainerTemplate
import net.vellity.dynamic.servers.templates.entity.template.ContainerTemplateProvider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceContainerTemplateController(
  private val containerTemplateProvider: ContainerTemplateProvider,
  private val templateGroupProvider: TemplateGroupProvider
) :
  ContainerTemplateController {
  override fun getTemplateWithId(containerTemplateId: String): ResponseEntity<ContainerTemplateDto?> {
    val template = containerTemplateProvider.getContainerTemplateById(containerTemplateId)
      ?: return ResponseEntity.notFound().build()
    val groups = templateGroupProvider.getGroupsOfTemplate(containerTemplateId)
    return ResponseEntity.ok(
      ContainerTemplateDto(
        image = template.image!!,
        environment = joinEnvironmentMaps(template, groups),
        startupPriority = template.startupPriority,
        minimumAlwaysOnline = template.minimumAlwaysOnline,
        internalTags = joinInternalTags(template, groups),
        maximumMemoryInMB = template.maximumMemoryInMB
      )
    )
  }

  private fun joinInternalTags(
    template: ContainerTemplate,
    templateGroups: List<TemplateGroup>
  ): List<String> {
    val internalTags = mutableListOf<String>()
    templateGroups.forEach { group ->
      group.tags.let { internalTags.addAll(it) }
    }
    template.internalTags.let { internalTags.addAll(it) }
    return internalTags
  }

  private fun joinEnvironmentMaps(
    template: ContainerTemplate,
    templateGroups: List<TemplateGroup>
  ): Map<String, String> {
    val environment = mutableMapOf<String, String>()
    templateGroups.forEach { group ->
      group.environment.let { environment.putAll(it) }
    }
    template.environment?.let { environment.putAll(it) }
    return environment
  }
}