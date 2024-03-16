package net.vellity.dynamic.servers.templates.http.client

data class ContainerTemplateInformation(
  val image: String,
  val environment: Map<String, String>,
  val startupPriority: Int,
  val minimumAlwaysOnline: Int,
  val internalTags: List<String>,
  val maximumMemoryInMB: Int,
)