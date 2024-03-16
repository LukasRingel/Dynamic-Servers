package net.vellity.dynamic.servers.templates.download

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.templates.download.source.FileSystemTemplateSource
import net.vellity.dynamic.servers.templates.download.source.TemplateSource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CacheIfEnabledTemplatesDownloadService(private val templateSource: TemplateSource) : TemplatesDownloadService {
  private val templateCache = mutableMapOf<String, ByteArray>()

  override fun refresh() {
    templateCache.clear()
  }

  override fun getTemplateAsGZip(containerTemplateId: String): ByteArray {
    if (templateCache.containsKey(containerTemplateId)) {
      val cachedTemplate = templateCache[containerTemplateId]!!
      logger.info("[${containerTemplateId}] Using cached template with ${cachedTemplate.size} bytes")
      return cachedTemplate
    }

    val startTimeMillis = System.currentTimeMillis()
    val templateAsGZip = templateSource.getTemplateAsGZip(containerTemplateId)
    logger.info("[${containerTemplateId}] Compressed directories to ${templateAsGZip.size} bytes in ${System.currentTimeMillis() - startTimeMillis}ms")

    if (cachingEnabled) {
      templateCache[containerTemplateId] = templateAsGZip
      logger.info("[${containerTemplateId}] Cached template with ${templateAsGZip.size} bytes")
    } else {
      logger.info("[${containerTemplateId}] Skipping caching of template since caching is disabled")
    }
    return templateAsGZip
  }

  companion object {
    private val cachingEnabled = environmentOrDefault("CACHING_ENABLED", "true").toBoolean()
    private val logger = LoggerFactory.getLogger(FileSystemTemplateSource::class.java)
  }
}