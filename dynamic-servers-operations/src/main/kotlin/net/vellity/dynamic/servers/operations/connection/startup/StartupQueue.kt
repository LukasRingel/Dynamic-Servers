package net.vellity.dynamic.servers.operations.connection.startup

interface StartupQueue {
  fun insertTemplateToQueue(template: String, priority: Int, environment: Map<String, String>)
}