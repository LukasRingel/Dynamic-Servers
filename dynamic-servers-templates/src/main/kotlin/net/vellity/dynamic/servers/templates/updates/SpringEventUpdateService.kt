package net.vellity.dynamic.servers.templates.updates

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SpringEventUpdateService(private val eventPublisher: ApplicationEventPublisher) : UpdateService {
  override fun update() {
    eventPublisher.publishEvent(UpdateRequestedEvent())
  }
}