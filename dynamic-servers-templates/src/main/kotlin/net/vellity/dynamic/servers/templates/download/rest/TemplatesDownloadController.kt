package net.vellity.dynamic.servers.templates.download.rest

import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

interface TemplatesDownloadController {
  @GetMapping("/api/download")
  fun downloadTemplate(@RequestParam("containerTemplateId") containerTemplateId: String): ResponseEntity<Resource>
}