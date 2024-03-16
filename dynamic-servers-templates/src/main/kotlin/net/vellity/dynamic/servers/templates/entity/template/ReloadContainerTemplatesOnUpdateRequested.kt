package net.vellity.dynamic.servers.templates.entity.template

import net.vellity.dynamic.servers.templates.updates.UpdateRequestedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ReloadContainerTemplatesOnUpdateRequested(private val containerTemplateProvider: ContainerTemplateProvider) :
  ApplicationListener<UpdateRequestedEvent> {
  override fun onApplicationEvent(event: UpdateRequestedEvent) {
    containerTemplateProvider.refresh()
  }
}