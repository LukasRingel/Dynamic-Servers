package net.vellity.dynamic.servers.templates.entity.rest.dto

data class ContainerTemplateDto(
  val image: String,
  val environment: Map<String, String>,
  val startupPriority: Int,
  val minimumAlwaysOnline: Int,
  val internalTags: List<String>,
  val maximumMemoryInMB: Int,
)
