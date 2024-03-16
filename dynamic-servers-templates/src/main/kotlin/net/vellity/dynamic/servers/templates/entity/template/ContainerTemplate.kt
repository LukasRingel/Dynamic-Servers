package net.vellity.dynamic.servers.templates.entity.template

import org.springframework.data.annotation.Id

class ContainerTemplate {
  @Id
  var id: String? = null

  var image: String? = ""

  var environment: Map<String, String>? = mutableMapOf()

  var startupPriority: Int = 1

  var minimumAlwaysOnline: Int = 0

  var pathsToCopy = mutableListOf<String>()

  var internalTags = mutableListOf<String>()

  var maximumMemoryInMB = 2048
}