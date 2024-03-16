package net.vellity.dynamic.servers.templates.download.rest

import net.vellity.dynamic.servers.templates.download.TemplatesDownloadService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class TemplateServiceDownloadController(private val downloadService: TemplatesDownloadService) :
  TemplatesDownloadController {
  override fun downloadTemplate(containerTemplateId: String): ResponseEntity<Resource> {
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT_WITH_FILE_NAME)
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .body(getTemplate(containerTemplateId))
  }

  private fun getTemplate(containerTemplateId: String): Resource {
    return ByteArrayResource(downloadService.getTemplateAsGZip(containerTemplateId))
  }

  companion object {
    private const val ATTACHMENT_WITH_FILE_NAME = "attachment; filename=\"templates.tar.gz\""
  }
}