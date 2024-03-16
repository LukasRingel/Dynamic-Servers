package net.vellity.dynamic.servers.common.http.server.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import org.springframework.stereotype.Service
import org.springframework.web.filter.GenericFilterBean

@Service
class RequestSourceNameFilter : GenericFilterBean() {
  override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
    if (skip) {
      chain?.doFilter(request, response)
      return
    }

    if (request == null || response == null || chain == null) {
      return
    }

    if ((request as HttpServletRequest).getHeader(SOURCE_NAME_HEADER) == null) {
      return
    }

    chain.doFilter(request, response)
  }

  companion object {
    const val SOURCE_NAME_HEADER = "X-SOURCE-NAME"
    private val skip = environmentOrDefault("SKIP_SOURCE_NAME_FILTER", "false").toBoolean()
  }
}