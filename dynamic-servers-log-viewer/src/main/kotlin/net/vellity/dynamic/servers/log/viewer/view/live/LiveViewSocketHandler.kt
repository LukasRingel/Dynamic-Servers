package net.vellity.dynamic.servers.log.viewer.view.live

import com.google.gson.JsonParser
import net.vellity.dynamic.servers.log.viewer.agent.AgentService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class LiveViewSocketHandler(
  private val agentService: AgentService,
  private val liveViewSessionService: LiveViewSessionService
) : TextWebSocketHandler() {
  override fun afterConnectionEstablished(session: WebSocketSession) {
    log.info(
      "New websocket from " +
        (session.remoteAddress?.toString()?.replace("/", "") ?: "??") + " connected."
    )
  }

  override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
    val jsonElement = JsonParser.parseString(message.payload.toString())
    val action = jsonElement.asJsonObject.get("action").asString

    if (action == "subscribe") {
      val target = jsonElement.asJsonObject.get("target").asString.split("@")
      liveViewSessionService.createSession(target[0], target[1], session.id) {
        session.sendMessage(TextMessage(it))
      }
      agentService.requestLiveLogFromAgent(target[1], target[0]) {
        session.sendMessage(TextMessage(it))
      }
    }
  }

  override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
    log.info(
      "Websocket from " +
        (session.remoteAddress?.toString()?.replace("/", "") ?: "??") + " disconnected."
    )
    liveViewSessionService.removeSession(session.id)
  }

  companion object {
    private val log = LoggerFactory.getLogger(LiveViewSocketHandler::class.java)
  }
}