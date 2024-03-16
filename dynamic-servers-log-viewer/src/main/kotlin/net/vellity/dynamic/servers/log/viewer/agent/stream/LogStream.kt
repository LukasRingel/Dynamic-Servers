package net.vellity.dynamic.servers.log.viewer.agent.stream

class LogStream(
  val serverId: String,
  val sessions: MutableList<String> = mutableListOf(),
  val logLines: MutableList<String> = mutableListOf()
)