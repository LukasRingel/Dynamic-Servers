package net.vellity.dynamic.servers.operations.operations.rest

import net.vellity.dynamic.servers.common.http.server.filter.RequestSourceNameFilter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/api/operations")
interface OperationsController {
  @PostMapping("/template/restart")
  fun restartTemplate(
    @RequestParam("templateId") templateId: String,
    @RequestHeader(RequestSourceNameFilter.SOURCE_NAME_HEADER) sourceName: String
  ): ResponseEntity<Unit>
}