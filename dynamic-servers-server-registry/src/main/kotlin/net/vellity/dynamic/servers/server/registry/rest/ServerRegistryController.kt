package net.vellity.dynamic.servers.server.registry.rest

import net.vellity.dynamic.servers.common.http.server.filter.RequestSourceNameFilter
import net.vellity.dynamic.servers.server.registry.rest.dto.AddTagToServerDto
import net.vellity.dynamic.servers.server.registry.rest.dto.CreateServerDto
import net.vellity.dynamic.servers.server.registry.rest.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.rest.dto.UpdateServerStatusDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/servers")
interface ServerRegistryController {
  @PostMapping
  fun createServer(
    @RequestHeader(RequestSourceNameFilter.SOURCE_NAME_HEADER) sourceName: String,
    @RequestBody createServerDto: CreateServerDto
  ): ResponseEntity<Unit>

  @DeleteMapping
  fun deleteServer(
    @RequestHeader(RequestSourceNameFilter.SOURCE_NAME_HEADER) sourceName: String,
    @RequestParam("serverId") serverId: String,
    @RequestParam("reason") reason: String
  ): ResponseEntity<Unit>

  @PutMapping("/status")
  fun updateServerStatus(
    @RequestHeader(RequestSourceNameFilter.SOURCE_NAME_HEADER) sourceName: String,
    @RequestBody updateServerStatusDto: UpdateServerStatusDto
  ): ResponseEntity<Unit>

  @PutMapping("/tags")
  fun addTagsToServer(
    @RequestHeader(RequestSourceNameFilter.SOURCE_NAME_HEADER) sourceName: String,
    @RequestBody addTagToServerDto: AddTagToServerDto
  ): ResponseEntity<Unit>

  @GetMapping
  fun allServers(): ResponseEntity<List<RegisteredServerDto>>

  @GetMapping("/template")
  fun allServersOfTemplate(
    @RequestParam("template") template: String
  ): ResponseEntity<List<RegisteredServerDto>>

  @GetMapping("/executor")
  fun allServersOnExecutor(
    @RequestParam("executor") executor: String
  ): ResponseEntity<List<RegisteredServerDto>>

  @GetMapping("/withoutTags")
  fun allServersWithoutTag(
    @RequestParam("tags") tags: String
  ): ResponseEntity<List<RegisteredServerDto>>
}