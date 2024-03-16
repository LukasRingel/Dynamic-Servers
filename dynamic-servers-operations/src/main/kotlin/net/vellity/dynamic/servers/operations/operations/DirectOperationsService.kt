package net.vellity.dynamic.servers.operations.operations

import net.vellity.dynamic.servers.operations.operations.impl.RestartTemplateOperation
import net.vellity.dynamic.servers.operations.connection.registry.ServerRegistry
import net.vellity.dynamic.servers.operations.connection.startup.StartupQueue
import net.vellity.dynamic.servers.operations.connection.templates.TemplatesService
import net.vellity.dynamic.servers.operations.watcher.WatcherRegistry
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class DirectOperationsService(
  private val serviceRegistry: ServerRegistry,
  private val watcherRegistry: WatcherRegistry,
  private val templatesService: TemplatesService,
  private val startupQueue: StartupQueue
) : OperationsService {
  override fun restartTemplate(templateId: String) {
    executor.submit {
      RestartTemplateOperation(
        templateId = templateId,
        serviceRegistry = serviceRegistry,
        watcherRegistry = watcherRegistry,
        templatesService = templatesService,
        startupQueue = startupQueue
      ).execute()
    }
  }

  companion object {
    private val executor = Executors.newCachedThreadPool()
  }
}