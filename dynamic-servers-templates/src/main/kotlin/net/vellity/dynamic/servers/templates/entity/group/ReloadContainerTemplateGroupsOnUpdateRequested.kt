package net.vellity.dynamic.servers.templates.entity.group

import net.vellity.dynamic.servers.templates.updates.UpdateRequestedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ReloadContainerTemplateGroupsOnUpdateRequested(private val templateGroupProvider: TemplateGroupProvider) :
  ApplicationListener<UpdateRequestedEvent> {
  override fun onApplicationEvent(event: UpdateRequestedEvent) {
    templateGroupProvider.refresh()
  }
}