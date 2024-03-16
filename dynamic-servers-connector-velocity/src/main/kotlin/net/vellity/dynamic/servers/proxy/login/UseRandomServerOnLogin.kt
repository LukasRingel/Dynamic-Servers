package net.vellity.dynamic.servers.proxy.login

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent
import com.velocitypowered.api.proxy.ProxyServer

class UseRandomServerOnLogin(private val proxyServer: ProxyServer) {
  @Subscribe(order = PostOrder.FIRST)
  private fun chooseRandomServer(event: PlayerChooseInitialServerEvent) {
    val servers = proxyServer.allServers
    if (servers.isEmpty()) {
      return
    }
    event.setInitialServer(servers.random())
  }
}