package net.vellity.dynamic.servers.log.storage.rest

import net.vellity.dynamic.servers.log.storage.rest.dto.DatabaseStatsDto
import net.vellity.dynamic.servers.log.storage.rest.dto.StoreLogRequestDto
import net.vellity.dynamic.servers.log.storage.rest.dto.StoredLogDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/api/storage")
interface LogStorageController {
  @PostMapping
  fun storeLog(@RequestBody request: StoreLogRequestDto): ResponseEntity<Unit>

  @GetMapping
  fun getLogData(@RequestParam("serverId") serverId: String): ResponseEntity<StoredLogDto>

  @GetMapping("/view")
  fun viewLog(@RequestParam("serverId") serverId: String): ResponseEntity<String>

  @GetMapping("/database")
  fun getDatabaseStats(): ResponseEntity<DatabaseStatsDto>
}