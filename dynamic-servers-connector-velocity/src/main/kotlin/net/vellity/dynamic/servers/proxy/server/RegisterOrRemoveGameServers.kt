package net.vellity.dynamic.servers.proxy.server

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import org.slf4j.Logger
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class RegisterOrRemoveGameServers(
  private val serverRegistry: ServerRegistry,
  private val proxyServer: ProxyServer
) {
  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
      this::tick,
      0,
      1,
      TimeUnit.SECONDS
    )
  }

  private fun tick() {
    val serversWithoutTag = serverRegistry.getServersWithoutTag(IGNORE_PROXY_REGISTRATION_TAG)
    removeServerFromVelocityIfNotPresentAnymore(serversWithoutTag)
    registerServersToVelocityIfNotPresentBefore(serversWithoutTag)
  }

  private fun removeServerFromVelocityIfNotPresentAnymore(serversWithoutTag: List<RegisteredServerDto>) {
    val serversToRemove = mutableListOf<ServerInfo>()
    for (server in proxyServer.allServers) {
      if (serversWithoutTag.any { it.id.toString() == server.serverInfo.name }) {
        continue
      }
      serversToRemove.add(server.serverInfo)
    }
    for (serverToRemove in serversToRemove) {
      proxyServer.unregisterServer(serverToRemove)
    }
  }

  private fun registerServersToVelocityIfNotPresentBefore(serversWithoutTag: List<RegisteredServerDto>) {
    for (serverDto in serversWithoutTag) {
      if (proxyServer.getServer(serverDto.id.toString()).isPresent) {
        continue
      }
      if (serverDto.status != ServerStatus.RUNNING) {
        continue
      }
      proxyServer.registerServer(
        ServerInfo(
          serverDto.id.toString(), InetSocketAddress.createUnresolved(
            replaceHostnameForLocalDevelopment(serverDto.hostname),
            serverDto.port
          )
        )
      )
    }
  }

  private fun replaceHostnameForLocalDevelopment(hostname: String): String {
    return if (hostname == "localhost") {
      "host.docker.internal"
    } else {
      hostname
    }
  }

  companion object {
    private const val IGNORE_PROXY_REGISTRATION_TAG = "IgnoreProxyRegistration"
  }
}