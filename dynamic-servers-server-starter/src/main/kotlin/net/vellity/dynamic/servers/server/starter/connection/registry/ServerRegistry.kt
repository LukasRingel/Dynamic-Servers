package net.vellity.dynamic.servers.server.starter.connection.registry

interface ServerRegistry {
  fun registerServer(serverId: String, template: String, port: Int, hostname: String, tags: List<String>, additionalEnvironmentVars: Map<String, String>)

  fun hostname(): String

  fun apiKey(): String
}