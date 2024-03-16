package net.vellity.dynamic.servers.log.viewer.search

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/api/search")
interface SearchScreenController {
  @GetMapping
  fun getWithFilter(
    @RequestParam("template") template: String,
    @RequestParam("executor") executor: String,
    @RequestParam("limit") limit: Int,
    @RequestParam("before") before: Long,
    @RequestParam("after") after: Long
  ): ResponseEntity<List<SearchResult>>
}