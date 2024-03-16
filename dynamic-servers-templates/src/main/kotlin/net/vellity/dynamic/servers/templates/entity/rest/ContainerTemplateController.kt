package net.vellity.dynamic.servers.templates.entity.rest

import net.vellity.dynamic.servers.templates.entity.rest.dto.ContainerTemplateDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/api/templates")
interface ContainerTemplateController {
  @GetMapping
  fun getTemplateWithId(@RequestParam("containerTemplateId") containerTemplateId: String):
    ResponseEntity<ContainerTemplateDto?>
}