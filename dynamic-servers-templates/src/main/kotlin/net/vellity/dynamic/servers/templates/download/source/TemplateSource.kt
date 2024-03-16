package net.vellity.dynamic.servers.templates.download.source

interface TemplateSource {
  fun getTemplateAsGZip(containerTemplateId: String): ByteArray
}