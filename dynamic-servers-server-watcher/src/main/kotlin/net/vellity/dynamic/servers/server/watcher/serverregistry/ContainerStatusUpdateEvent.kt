package net.vellity.dynamic.servers.server.watcher.serverregistry

import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import org.springframework.context.ApplicationEvent

class ContainerStatusUpdateEvent(
  val server: RegisteredServerDto,
  val newStatus: ServerStatus
) : ApplicationEvent(Any())