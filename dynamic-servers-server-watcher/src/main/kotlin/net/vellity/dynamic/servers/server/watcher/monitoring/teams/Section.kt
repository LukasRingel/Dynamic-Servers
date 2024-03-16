package net.vellity.dynamic.servers.server.watcher.monitoring.teams

data class Section(
  val activityTitle: String = "",
  val activitySubtitle: String = "",
  val facts: List<Content>,
  val markdown: Boolean = false
)