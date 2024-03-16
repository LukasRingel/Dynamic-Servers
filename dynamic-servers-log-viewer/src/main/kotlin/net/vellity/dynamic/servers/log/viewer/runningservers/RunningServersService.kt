package net.vellity.dynamic.servers.log.viewer.runningservers

import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto

interface RunningServersService {
  fun getAllRunningServers(): List<RegisteredServerDto>
}