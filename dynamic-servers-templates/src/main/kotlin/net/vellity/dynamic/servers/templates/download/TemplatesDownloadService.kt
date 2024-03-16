package net.vellity.dynamic.servers.templates.download

interface TemplatesDownloadService {
  fun refresh()

  fun getTemplateAsGZip(containerTemplateId: String): ByteArray
}