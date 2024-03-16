package net.vellity.dynamic.servers.log.viewer.runningservers

import net.vellity.dynamic.servers.server.registry.http.client.ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import org.springframework.stereotype.Service

@Service
class ServerRegistryRunningServersService(private val serverRegistryClient: ServerRegistryClient) :
  RunningServersService {
  override fun getAllRunningServers(): List<RegisteredServerDto> {
    return serverRegistryClient.allServers()
  }
}