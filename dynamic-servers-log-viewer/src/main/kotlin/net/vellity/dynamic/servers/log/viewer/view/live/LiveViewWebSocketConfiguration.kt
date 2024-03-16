package net.vellity.dynamic.servers.log.viewer.view.live

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
open class LiveViewWebSocketConfiguration(private val liveViewSocketHandler: LiveViewSocketHandler) :
  WebSocketConfigurer {
  override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
    registry.addHandler(liveViewSocketHandler, "/ws/log").setAllowedOrigins("*")
  }
}