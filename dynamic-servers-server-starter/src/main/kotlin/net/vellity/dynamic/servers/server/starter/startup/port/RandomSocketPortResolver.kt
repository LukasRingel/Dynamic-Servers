package net.vellity.dynamic.servers.server.starter.startup.port

import org.springframework.stereotype.Component
import java.net.ServerSocket

@Component
class RandomSocketPortResolver : PortResolver {
  override fun findFreePort(): Int {
    var port = getRandomPortInRange()
    while (!isPortFree(port)) {
      port = getRandomPortInRange()
    }
    return port
  }

  private fun isPortFree(port: Int): Boolean =
    try {
      ServerSocket(port).close()
      true
    } catch (e: Exception) {
      false
    }

  private fun getRandomPortInRange(): Int =
    (START_PORT..END_PORT).random()

  companion object {
    private const val START_PORT = 31000
    private const val END_PORT = 32000
  }
}