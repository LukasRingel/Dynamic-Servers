package net.vellity.dynamic.servers.templates.download

import net.vellity.dynamic.servers.templates.updates.UpdateRequestedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ClearDownloadCacheOnUpdateRequested(private val templatesDownloadService: TemplatesDownloadService) :
  ApplicationListener<UpdateRequestedEvent> {
  override fun onApplicationEvent(event: UpdateRequestedEvent) {
    templatesDownloadService.refresh()
  }
}