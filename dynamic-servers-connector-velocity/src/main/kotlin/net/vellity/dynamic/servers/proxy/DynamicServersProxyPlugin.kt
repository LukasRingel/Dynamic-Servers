package net.vellity.dynamic.servers.proxy

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import net.vellity.dynamic.servers.proxy.login.UseRandomServerOnLogin
import net.vellity.dynamic.servers.proxy.server.RegisterOrRemoveGameServers
import net.vellity.dynamic.servers.proxy.server.ServerRegistry
import net.vellity.dynamic.servers.proxy.server.ServiceServerRegistry
import org.slf4j.Logger

@Plugin(
  id = "dynamic-servers-proxy",
  name = "dynamic-servers-proxy",
  version = "1.0-SNAPSHOT",
  description = "Proxy plugin for Dynamic Servers",
  authors = ["Vellity"]
)
class DynamicServersProxyPlugin @Inject constructor(private val proxyServer: ProxyServer) {
  private val serverRegistry: ServerRegistry = ServiceServerRegistry()

  @Subscribe
  private fun initialize(event: ProxyInitializeEvent) {
    startVelocityServerRegistration()
    registerRandomServerOnLogin()
  }

  private fun startVelocityServerRegistration() {
    RegisterOrRemoveGameServers(serverRegistry, proxyServer)
  }

  private fun registerRandomServerOnLogin() {
    proxyServer.eventManager.register(this, UseRandomServerOnLogin(proxyServer))
  }
}